package com.xtxk.recognition.prepare.service.dto.AlarmPolicyConfiguration;

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

    @ApiModelProperty(value = "该算法成功告警后，间隔多少秒停止采集")
    private Integer interval;

    @ApiModelProperty(value = "该算法持续多久，目前止违规停车有这个值")
    private Integer duration;

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

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }
}
