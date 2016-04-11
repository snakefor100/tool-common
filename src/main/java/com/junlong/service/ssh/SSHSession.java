package com.junlong.service.ssh;

import java.io.Closeable;
import java.util.List;

/**
 * Created by niuniu on 2016/4/10.
 */
public interface SSHSession extends Closeable{
    public void execute(String commond,SSHExecuteCallBack callBack);
}
