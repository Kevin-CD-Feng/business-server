package com.xtxk.cn.common;

import java.util.List;

/**
 * DeviceStateResult
 *
 * @author chenzhi
 * @date 2022/11/2 14:22
 * @description
 */
public class DeviceStateResult<T> {

    private Result result;

    private Integer responseCode;

    private String responseDesc;

    private List<T> resources;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

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

    public List<T> getResources() {
        return resources;
    }

    public void setResources(List<T> resources) {
        this.resources = resources;
    }
}
