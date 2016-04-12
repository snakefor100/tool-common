package com.junlong.service.ssh;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.junlong.common.domain.Constants;
import com.junlong.common.domain.exception.BusinessException;
import com.junlong.common.domain.exception.ResponseCode;
import com.junlong.common.domain.ssh.SSHRequest;
import com.junlong.utils.ssh.SSHSessionFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by niuniu on 2016/4/8.
 */
@Deprecated
public class SSHServiceImpl implements SSHService {
    private static Logger logger = Logger.getLogger(SSHServiceImpl.class);
    private static final String LOG_SSH_SERVICE = "SSH服务-";

    private SSHRequest sshRequest;

    public SSHServiceImpl(SSHRequest sshRequest) {
        this.sshRequest = sshRequest;
    }

    @Override
    public List<String> execute(String commond) {
        logger.info(LOG_SSH_SERVICE+"开始执行命令:"+commond);
        Connection connection = SSHSessionFactory.create(sshRequest.getIp(), sshRequest.getPort(), sshRequest.getUserName(), sshRequest.getPassWord());
        List<String> result = null;
        try {
            Session session = connection.openSession();
            session.execCommand(Constants.COMMAND_TOP);
            BufferedReader read = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStdout())));
            result = new ArrayList<String>();
            String line = null;
            while ((line = read.readLine()) != null){
                if(StringUtils.isEmpty(line)){
                    continue;
                }
                result.add(line);
            }
        } catch (IOException e) {
            logger.error(LOG_SSH_SERVICE + "执行"+commond+"时发生未知异常,异常信息:" + e.getMessage(), e);
            throw new BusinessException(ResponseCode.SYSTEM, e);
        }finally {
            SSHSessionFactory.close(connection);
            logger.info(LOG_SSH_SERVICE+"命令返回结果:"+result);
            return result;
        }
    }
}
