package com.junlong.common.domain;

import java.util.Map;

/**
 * Created by niuniu on 2016/4/2.
 */
public class PageRequestBean {
    private int page = 10;
    private int rows;
    private Map<String,Object> fields;
    private Map<String,String> srotItemMap;
    private int start;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public Map<String, String> getSrotItemMap() {
        return srotItemMap;
    }

    public void setSrotItemMap(Map<String, String> srotItemMap) {
        this.srotItemMap = srotItemMap;
    }


    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "PageRequestBean{" +
                "page=" + page +
                ", rows=" + rows +
                ", fields=" + fields +
                ", srotItemMap=" + srotItemMap +
                ", start=" + start +
                '}';
    }
}
