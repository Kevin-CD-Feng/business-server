package com.xtxk.cn.dto.AlarmPolicyConfiguration;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AlarmPolicyConfigurationItemDto {

    @ApiModelProperty(value = "供列表序号使用", required = true)
    private Integer id;

    @ApiModelProperty(value = "设备-resourceId", required = true)
    private String resourceId;

    @ApiModelProperty(value = "事件code", required = true)
    private String eventCode;

    @ApiModelProperty(value = "违规时长", required = true)
    private String violationLength;

    @ApiModelProperty(value = "视频尺寸对象", required = true)
    private List<WebCoordinateDto> webCoordinateDtos;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getViolationLength() {
        return violationLength;
    }

    public void setViolationLength(String violationLength) {
        this.violationLength = violationLength;
    }

    public List<WebCoordinateDto> getWebCoordinateDtos() {
        return webCoordinateDtos;
    }

    public void setWebCoordinateDtos(List<WebCoordinateDto> webCoordinateDtos) {
        this.webCoordinateDtos = webCoordinateDtos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
