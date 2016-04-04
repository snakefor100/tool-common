package com.junlong.utils;

/**
 * Created by niuniu on 2016/4/4.
 */
public class IntegerUtil {
    public static Integer defaultIfSmallerThan0( Integer originalInt, Integer defaultInt ) {
        if ( 0 >= originalInt ) {
            return defaultInt;
        }
        return originalInt;
    }
}
