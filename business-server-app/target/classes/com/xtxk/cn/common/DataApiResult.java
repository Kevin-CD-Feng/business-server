package com.xtxk.cn.common;

import java.util.List;

/**
 * DataApiResult
 *
 * @author chenzhi
 * @date 2022/10/31 14:12
 * @description
 */
public class DataApiResult<T> {
    private Integer responseCode;

    private String responseDesc;

    private List<T> returnValue;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public List<T> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(List<T> returnValue) {
        this.returnValue = returnValue;
    }
}
