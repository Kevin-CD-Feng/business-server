package com.xtxk.cn.dto.AlarmPolicyConfiguration;

import io.swagger.annotations.ApiModelProperty;

public class ConfigurationItemDto {
    @ApiModelProperty(value = "算法code")
    private String algorithmCode;

    @ApiModelProperty(value = "算法识别频率")
    private Integer peroid;

    @ApiModelProperty(value = "roi")
    private String roi;

    @ApiModelProperty(value = "资源id")
    private String resourceId;

    @ApiModelProperty(value = "资源name")
    private String resourceName;

    @ApiModelProperty(value = "事件code")
    private String eventCode;

    public String getAlgorithmCode() {
        return algorithmCode;
    }

    public void setAlgorithmCode(String algorithmCode) {
        this.algorithmCode = algorithmCode;
    }

    public Integer getPeroid() {
        return peroid;
    }

    public void setPeroid(Integer peroid) {
        this.peroid = peroid;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }
}
