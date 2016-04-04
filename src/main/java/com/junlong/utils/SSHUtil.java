package com.junlong.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.junlong.common.domain.Constants;
import com.junlong.common.domain.exception.BusinessException;
import com.junlong.common.domain.exception.ResponseCode;
import com.junlong.common.domain.ssh.HostPerformanceEntity;
import com.junlong.common.domain.ssh.SSHResource;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SSH工具类
 * Created by niuniu on 2016/4/4.
 */
public class SSHUtil {
    public static HostPerformanceEntity getHostPerformance(String ip, int port, String userName, String password ){
        if(StringUtil.isBlank(ip)){
            throw new BusinessException(ResponseCode.PARAM);
        }
        port = IntegerUtil.defaultIfSmallerThan0(port,Constants.SSH_PORT);
        userName = StringUtil.defaultIfBlank(userName, Constants.SSH_USERNAME);
        password = StringUtil.defaultIfBlank(password,Constants.SSH_PASSWORD);
        Connection connection = null;
        try {
            connection = new Connection(ip,port);
            connection.connect(null, 2000, 2000);
            boolean isAuthticated = connection.authenticateWithPassword(userName, password);
            return getHostPerformance(connection);
        }catch (Exception e){
            throw new BusinessException(ResponseCode.SSHAUTH,e);
        }finally {
            if(null != connection){
                connection.close();
            }
        }
    }
    public static HostPerformanceEntity getHostPerformance(Connection connection){
        HostPerformanceEntity hostPerformanceEntity = null;
        Session session = null;
        BufferedReader read = null;
        try{
            hostPerformanceEntity = new HostPerformanceEntity();
            hostPerformanceEntity.setIp(connection.getHostname());
            session = connection.openSession();
            session.execCommand(Constants.COMMAND_TOP);
            read = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStdout())));
            String line = "";
            int lineNum = 0;
            String totalMem = Constants.EMPTY_STRING;
            String freeMem = Constants.EMPTY_STRING;
//            String freeMem = Constants.EMPTY_STRING;
//            String buffersMem = Constants.EMPTY_STRING;
//            String cachedMem = Constants.EMPTY_STRING;
            while ((line = read.readLine()) != null){
                if(StringUtil.isBlank(line)){
                    continue;
                }
                lineNum += 1;
                if( 5 < lineNum){
                    return hostPerformanceEntity;
                }
                if( 1 == lineNum){
                    // 第一行，通常是这样：
                    // top - 19:58:52 up 416 days, 30 min, 1 user, load average:
                    // 0.00, 0.00, 0.00
                    int loadAverageIndex = line.indexOf(Constants.LOAD_AVERAGE_STRING);
                    String loadAverages = line.substring( loadAverageIndex ).replace(Constants.LOAD_AVERAGE_STRING, Constants.EMPTY_STRING );
                    String[] loadAverageArray = loadAverages.split( "," );
                    if ( 3 != loadAverageArray.length ){
                        continue;
                    }
                    hostPerformanceEntity.setLoad( StringUtil.trimToEmpty( loadAverageArray[0] ) );
                }else if( 3==lineNum){
                    // 第三行通常是这样：
                    // Cpu(s): 0.0% us, 0.0% sy, 0.0% ni, 100.0% id, 0.0% wa,
                    // 0.0% hi, 0.0% si
                    String cpuUsage = line.split( "," )[0].replace(Constants.CPU_USAGE_STRING, Constants.EMPTY_STRING ).replace( "us", Constants.EMPTY_STRING );
                    hostPerformanceEntity.setCpuUsage( StringUtil.trimToEmpty( cpuUsage ) );
                }else if ( 4 == lineNum ) {
                    // 第四行通常是这样：
                    // Mem: 1572988k total, 1490452k used, 82536k free, 138300k
                    // buffers

                    // updated by hengyunabc
                    // 有可能是这样的：
                    // KiB Mem:   8085056 total,  7557820 used,   527236 free,   385016 buffers
                    // 所以这里的字符串处理不对，直接改为匹配数字即可。
                    List<String> list = StringUtil.findAllByRegex(line, "\\d+");
                    totalMem = list.get(0);
                    freeMem = list.get(2);

                } else if ( 5 == lineNum ) {
                    // 第四行通常是这样：
                    // Swap: 2096472k total, 252k used, 2096220k free, 788540k
                    // cached
                    List<String> list = StringUtil.findAllByRegex(line, "\\d+");

                    if ( StringUtil.isBlank( totalMem,freeMem) ){
                        throw new BusinessException(ResponseCode.DATAERROR);
                    }

                    Double totalMemDouble = Double.parseDouble( totalMem );
                    Double freeMemDouble = Double.parseDouble( freeMem );

//                    Double memoryUsage = 1 - ( ( freeMemDouble + buffersMemDouble + cachedMemDouble ) / totalMemDouble );
                    Double memoryUsage = 1-(freeMemDouble/ totalMemDouble) ;
                    hostPerformanceEntity.setMemoryUsage( memoryUsage * 100 + Constants.PERCENT );
                } else {
                    continue;
                }
            }
            // 统计磁盘使用状况
            Map< String, String > diskUsageMap = new HashMap< String, String >();
            session = connection.openSession();
            session.execCommand( Constants.COMMAND_DF_LH );
            read = new BufferedReader( new InputStreamReader( new StreamGobbler( session.getStdout() ) ) );

            /**
             * 内容通常是这样： Filesystem 容量 已用 可用 已用% 挂载点 /dev/xvda2 5.8G 3.2G 2.4G
             * 57% / /dev/xvda1 99M 8.0M 86M 9% /boot none 769M 0 769M 0%
             * /dev/shm /dev/xvda7 68G 7.1G 57G 12% /home /dev/xvda6 2.0G 36M
             * 1.8G 2% /tmp /dev/xvda5 2.0G 199M 1.7G 11% /var
             * */
            boolean isFirstLine = true;
            while ( ( line = read.readLine() ) != null ) {
                if ( isFirstLine ) {
                    isFirstLine = false;
                    continue;
                }
                if ( StringUtil.isBlank( line ) )
                    continue;

                line = line.replaceAll( " {1,}", Constants.WORD_SEPARATOR );
                String[] lineArray = line.split(Constants.WORD_SEPARATOR );
                if ( 6 != lineArray.length ) {
                    continue;
                }
                String diskUsage = lineArray[4];
                String mountedOn = lineArray[5];
                diskUsageMap.put( mountedOn, diskUsage );
            }
            hostPerformanceEntity.setDiskUsageMap( diskUsageMap );
        }catch (Exception e){
            throw new BusinessException(ResponseCode.SSHERROR,e);
        }
        return  hostPerformanceEntity;
    }

    public static SSHResource executeWithoutHandleBufferedReader(String ip, int port, String username, String password, String command ){
        if ( StringUtil.isBlank( command ) )
            return null;
        port = IntegerUtil.defaultIfSmallerThan0( port, 22 );
        SSHResource sshResource = new SSHResource();
        try {
            if (StringUtil.isBlank( ip )) {
                throw new BusinessException(ResponseCode.PARAM);
            }
            username = StringUtil.defaultIfBlank( username, Constants.SSH_USERNAME );
            password = StringUtil.defaultIfBlank( password, Constants.SSH_PASSWORD );
            sshResource.conn = new Connection( ip, port );
            sshResource.conn.connect( null, 2000, 2000 );
            boolean isAuthenticated = sshResource.conn.authenticateWithPassword( username, password );
            if ( isAuthenticated == false ) {
                throw new BusinessException(ResponseCode.SSHAUTH);
            }

            sshResource.session = sshResource.conn.openSession();
            sshResource.session.execCommand( "echo stat | nc 192.168.1.170 2181" );
            System.out.println(sshResource.session.getStdout() );
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new StreamGobbler(sshResource.session.getStdout())));
            System.out.println(bufferedReader.readLine());
            sshResource.setReader( new BufferedReader( new InputStreamReader( new StreamGobbler( sshResource.session.getStdout() ) ) ) );

            return sshResource;
        } catch ( Exception e ) {
            throw new BusinessException(ResponseCode.DATAERROR);
        }
    }
    private static final String MODE_FOLLOWER = "Mode: follower";
    private static final String MODE_LEADERER = "Mode: leader";
    private static final String MODE_STANDALONE = "Mode: standalone";
    private static final String MODE_OBSERVER = "Mode: observer";
    private static final String NODE_COUNT = "Node count:";
    public static void main(String[] args) throws IOException {
        SSHResource root = executeWithoutHandleBufferedReader("192.168.1.170", 22, "root", "123456", StringUtil.replaceSequenced(Constants.COMMAND_STAT, "192.168.1.170", 2181
                + Constants.COMMAND_STAT));
        String line = "";
        BufferedReader bufferedRead = root.reader;
        while ((line = bufferedRead.readLine()) != null) {
          if (line.contains(MODE_FOLLOWER)) {
                System.out.println("F");
            } else if (line.contains(MODE_LEADERER)) {
                System.out.println("L");
            } else if (line.contains(MODE_STANDALONE)) {
                System.out.println("S");
            } else if (line.contains(MODE_OBSERVER)) {
                System.out.println("O");
            }
        }
    }
}
