package com.junlong.common.domain.exception;

/**
 * Created by niuniu on 2016/4/2.
 */
public class BusinessException extends RuntimeException {
    private ResponseCode responseCode;

    public BusinessException(ResponseCode error) {
        super("ZK监控发生异常，异常COD:[" + error.getCode() + "],异常信息:[" + error.getMsg());
        this.responseCode = error;
    }

    public BusinessException(ResponseCode error, Throwable e) {
        super("ZK监控发生异常，异常COD:[" + error.getCode() + "],异常信息:[" + error.getMsg() + "\t" + e.getMessage() + "]", e);
        this.responseCode = error;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
