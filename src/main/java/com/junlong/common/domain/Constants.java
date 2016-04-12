package com.junlong.common.domain;

/**
 * Created by niuniu on 2016/4/4.
 */
public class Constants {
    public static final String SSH_USERNAME = "root";
    public static final String SSH_PASSWORD = "123456";
    public final static int SSH_PORT        = 22;
    public final static String EMPTY_STRING = "";
    public final static String COMMAND_TOP = "top -b -n 1 | head -5";
    public final static String COMMAND_DF_LH = "df -lh";
    public final static String LOAD_AVERAGE_STRING = "load average: ";
    public final static String CPU_USAGE_STRING = "Cpu(s):";
    public final static String PERCENT = "%";
    public static final String WORD_SEPARATOR = Character.toString( ( char )2 );


    public final static int SSH_TIMEOUT = 6000;
    /*****************  符号  **********************/
    public final static String COMMA = ",";
    public final static String COLON = ":";

}
