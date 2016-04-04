package com.junlong.common.domain;

import com.junlong.common.domain.exception.ResponseCode;

/**
 * Created by niuniu on 2016/4/2.
 */
public abstract class BaseResponseBean {
    private boolean success;
    private int code;
    private String msg;

    public BaseResponseBean(ResponseCode responseCode) {
        code = responseCode.getCode();
        success = (code== ResponseCode.SUCCESS.getCode()?true:false);
        msg = responseCode.getMsg();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
