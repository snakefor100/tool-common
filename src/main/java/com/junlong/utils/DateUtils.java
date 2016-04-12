package com.junlong.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by niuniu on 2016/4/11.
 */
public class DateUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 日期转换:long转字符串
     * @param time
     * @return
     */
    public static String long2String(long time) {
        return sdf.format(new Date(time));
    }

}
