package com.junlong.common.generator;

import java.util.UUID;

/**
 * Created by niuniu on 2016/3/29.
 */
public class IdGenerator {
    /**
     * function Id生成器
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
