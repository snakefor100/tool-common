package com.junlong.common.domain.exception;

/**
 * Created by niuniu on 2016/4/2.
 */
public enum ResponseCode {
    SUCCESS(0,"success"),PARAM(1,"参数不合法"),SYSTEM(2,"系统异常"),SSHAUTH(3,"SSH连接异常,验证失败"),DATAERROR(4,"数据有异常"),SSHERROR(5,"SSH过程中遇到异常"),INIT(6,"系统初始化异常");
    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
