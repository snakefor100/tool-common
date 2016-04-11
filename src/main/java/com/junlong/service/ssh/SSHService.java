package com.junlong.service.ssh;

import ch.ethz.ssh2.Session;
import com.junlong.common.domain.ssh.SSHRequest;

import java.util.List;

/**
 * Created by niuniu on 2016/4/8.
 */
@Deprecated
public interface SSHService {
    public List<String> execute(String commond);
}
