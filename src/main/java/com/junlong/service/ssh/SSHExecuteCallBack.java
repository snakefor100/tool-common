package com.junlong.service.ssh;

import java.io.BufferedReader;

/**
 * Created by niuniu on 2016/4/10.
 */
public interface SSHExecuteCallBack {
    void processResult(BufferedReader read);
}
