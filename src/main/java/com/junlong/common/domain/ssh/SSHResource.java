package com.junlong.common.domain.ssh;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.junlong.utils.IOUtil;

import java.io.BufferedReader;

/**
 * Created by niuniu on 2016/4/4.
 */
public class SSHResource {
    public Connection conn = null;
    public Session session = null;
    public BufferedReader reader = null;

    /** 关闭SSH操作所有占用的资源 */
    public void closeAllResource() {
        IOUtil.closeReader( reader );
        if ( null != session )
            session.close();
        if ( null != conn )
            conn.close();
    }

    public void setConn( Connection conn ) {
        this.conn = conn;
    }

    public void setSession( Session session ) {
        this.session = session;
    }

    public void setReader( BufferedReader reader ) {
        this.reader = reader;
    }
}
