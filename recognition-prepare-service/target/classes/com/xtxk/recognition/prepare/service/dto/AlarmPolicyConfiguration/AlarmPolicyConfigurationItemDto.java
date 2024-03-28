package com.xtxk.recognition.prepare.service.dto.AlarmPolicyConfiguration;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class AlarmPolicyConfigurationItemDto {

    @ApiModelProperty(value = "供列表序号使用", required = true)
    private Integer id;

    @ApiModelProperty(value = "设备-resourceId", required = true)
    private String resourceId;

    @ApiModelProperty(value = "事件code", required = true)
    private String eventCode;

    @ApiModelProperty(value = "事件CodeDesc", required = true)
    private String eventCodeDesc;

    @ApiModelProperty(value = "算法code", required = true)
    private String algCode;

    @ApiModelProperty(value = "算法CodeDesc", required = true)
    private String algCodeDesc;

    @ApiModelProperty(value = "违规时长", required = true)
    private String violationLength;

    @ApiModelProperty(value = "违规间隔时长", required = true)
    private Integer violationInterval;

    @ApiModelProperty(value = "违规持续时长", required = true)
    private Integer violationDuration;

    @ApiModelProperty(value = "违规开始时间段")
    private Date violationBeginDate;

    @ApiModelProperty(value = "违规结束时间段")
    private Date violationEndDate;

    @ApiModelProperty(value = "视频尺寸对象", required = true)
    private List<WebCoordinateDto> webCoordinateDtos;

    @ApiModelProperty(value = "检测频率,单位秒",required = true)
    private Float frameInterval;

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

    public String getAlgCode() {
        return algCode;
    }

    public void setAlgCode(String algCode) {
        this.algCode = algCode;
    }

    public Integer getViolationInterval() {
        return violationInterval;
    }

    public void setViolationInterval(Integer violationInterval) {
        this.violationInterval = violationInterval;
    }

    public Integer getViolationDuration() {
        return violationDuration;
    }

    public void setViolationDuration(Integer violationDuration) {
        this.violationDuration = violationDuration;
    }

    public Date getViolationBeginDate() {
        return violationBeginDate;
    }

    public void setViolationBeginDate(Date violationBeginDate) {
        this.violationBeginDate = violationBeginDate;
    }

    public Date getViolationEndDate() {
        return violationEndDate;
    }

    public void setViolationEndDate(Date violationEndDate) {
        this.violationEndDate = violationEndDate;
    }

    public String getEventCodeDesc() {
        return eventCodeDesc;
    }

    public void setEventCodeDesc(String eventCodeDesc) {
        this.eventCodeDesc = eventCodeDesc;
    }

    public String getAlgCodeDesc() {
        return algCodeDesc;
    }

    public void setAlgCodeDesc(String algCodeDesc) {
        this.algCodeDesc = algCodeDesc;
    }

    public Float getFrameInterval() {
        return frameInterval;
    }

    public void setFrameInterval(Float frameInterval) {
        this.frameInterval = frameInterval;
    }
}
