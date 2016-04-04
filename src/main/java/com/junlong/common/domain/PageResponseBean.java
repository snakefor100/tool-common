package com.junlong.common.domain;

import com.junlong.common.domain.exception.ResponseCode;

import java.util.List;

/**
 * Created by niuniu on 2016/4/2.
 */
public class PageResponseBean<T> extends BaseResponseBean {
    private long total;
    private List<T> rows;

    public PageResponseBean() {
        super(ResponseCode.SUCCESS);
    }

    public PageResponseBean(ResponseCode responseCode) {
        super(responseCode);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageResponseBean{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
