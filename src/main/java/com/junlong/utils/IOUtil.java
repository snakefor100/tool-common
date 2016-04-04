package com.junlong.utils;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by niuniu on 2016/4/4.
 */
public class IOUtil {
    /** 关闭  Reader 流 */
    public static void closeReader( Reader reader ){
        if( null != reader ){
            try {
                reader.close();
            } catch ( IOException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
