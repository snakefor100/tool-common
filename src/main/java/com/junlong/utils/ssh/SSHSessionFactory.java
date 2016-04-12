package com.junlong.utils.ssh;

import ch.ethz.ssh2.Connection;
import com.junlong.common.domain.Constants;
import com.junlong.common.domain.exception.BusinessException;
import com.junlong.common.domain.exception.ResponseCode;
import com.junlong.utils.IntegerUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by niuniu on 2016/4/7.
 */
public class SSHSessionFactory {
    private static Logger logger = Logger.getLogger(SSHSessionFactory.class);
    private static final String LOG_SSH_FACTORY = "SSH工厂-";

    public static Connection create(String ip, int port, String userName, String password) {
        logger.info(LOG_SSH_FACTORY + "创建Session,参数: ip[" + ip + "],port[" + port + "],userName[" + userName + "],password[" + password + "]");
        if (StringUtils.isBlank(ip)) {
            logger.error(LOG_SSH_FACTORY + "异常,IP为空");
            throw new BusinessException(ResponseCode.PARAM);
        }
        port = IntegerUtil.defaultIfSmallerThan0(port, Constants.SSH_PORT);
        userName = StringUtils.defaultString(userName, Constants.SSH_USERNAME);
        password = StringUtils.defaultString(password, Constants.SSH_PASSWORD);
        Connection connection = null;
        connection = new Connection(ip, port);
        try {
            connection.connect(null, Constants.SSH_TIMEOUT, Constants.SSH_TIMEOUT*2);
            boolean isAuthticated = connection.authenticateWithPassword(userName, password);
            if (!isAuthticated) {
                logger.error("SSH连接异常,用户名密码错误！");
                throw new BusinessException(ResponseCode.SSHAUTH);
            }

        } catch (IOException e) {
            if(e.getMessage().contains("Password authentication failed")){
                logger.error("测试:"+userName+","+password+","+ip+","+port);
            }
            logger.error(LOG_SSH_FACTORY + "创建连接时发生未知异常,异常信息:" + e.getMessage(), e);
            throw new BusinessException(ResponseCode.SYSTEM, e);
        }
        return connection;
    }

    public static void close(Connection connection) {
        if (connection != null) {
            connection.close();
        }
    }

}
