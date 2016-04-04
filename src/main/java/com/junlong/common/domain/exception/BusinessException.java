package com.junlong.common.domain.exception;

/**
 * Created by niuniu on 2016/4/2.
 */
public class BusinessException extends RuntimeException{
    public BusinessException(ResponseCode error) {
        super("ZK监控发生异常，异常COD:["+error.getCode()+"],异常信息:["+error.getMsg());
    }
    public BusinessException(ResponseCode error, Throwable e) {
        super("ZK监控发生异常，异常COD:["+error.getCode()+"],异常信息:["+error.getMsg()+"\t"+e.getMessage()+"]",e);
    }
}
