package com.junlong.service.ssh;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.junlong.common.domain.Constants;
import com.junlong.common.domain.exception.BusinessException;
import com.junlong.common.domain.exception.ResponseCode;
import com.junlong.common.domain.ssh.SSHRequest;
import com.junlong.utils.StringUtil;
import com.junlong.utils.ssh.SSHSessionFactory;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by niuniu on 2016/4/10.
 */
public class SSHServiceTemplate implements SSHSession {
    private static Logger logger = Logger.getLogger(SSHServiceTemplate.class);
    private static final String LOG_SSH_SERVICE = "SSH模板-";
    private SSHRequest sshRequest;

    public SSHServiceTemplate(SSHRequest sshRequest) {
        this.sshRequest = sshRequest;
    }


    @Override
    public void execute(String commond, SSHExecuteCallBack callBack) {
        logger.info(LOG_SSH_SERVICE + "开始执行命令:" + commond);
        Connection connection = SSHSessionFactory.create(sshRequest.getIp(), sshRequest.getPort(), sshRequest.getUserName(), sshRequest.getPassWord());
        try {
            Session session = connection.openSession();
            session.execCommand(commond);
            BufferedReader read = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStdout())));
            callBack.processResult(read);
            session.close();
        } catch (IOException e) {
            logger.error(LOG_SSH_SERVICE + "执行" + commond + "时发生未知异常,异常信息:" + e.getMessage(), e);
            throw new BusinessException(ResponseCode.SYSTEM, e);
        } finally {
            SSHSessionFactory.close(connection);
        }
    }

    @Override
    public void close() throws IOException {
        //执行execut时自动关闭，暂时不需要close方法
        throw new BusinessException(ResponseCode.SYSTEM);
    }

}
