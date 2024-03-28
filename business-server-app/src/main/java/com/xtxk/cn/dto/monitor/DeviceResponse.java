package com.xtxk.cn.dto.monitor;

import java.io.Serializable;
import java.util.List;

/**
 * @author HW
 */
public class DeviceResponse implements Serializable {

    private static final long serialVersionUID = 390787402440917354L;

    private Integer responseCode;

    private String responseDesc;

    private List<DeviceDTO> returnValue;

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

    public List<DeviceDTO> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(List<DeviceDTO> returnValue) {
        this.returnValue = returnValue;
    }
}
