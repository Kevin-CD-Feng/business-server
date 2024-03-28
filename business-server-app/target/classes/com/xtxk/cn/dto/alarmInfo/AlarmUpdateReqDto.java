package com.xtxk.cn.dto.alarmInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class AlarmUpdateReqDto {

    @ApiModelProperty(value = "id",required = true)
    private Integer id;

    @ApiModelProperty(value = "告警来源",required = true)
    private String sourceName;

    @ApiModelProperty(value = "告警事件",required = true)
    private String eventName;

    @ApiModelProperty(value = "告警时间",required = true)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date alarmTime;

    @ApiModelProperty(value = "事件状态",required = true)
    private String status;

    @ApiModelProperty(value = "抓拍图片",required = true)
    private String catchImageUrl;

    @ApiModelProperty(value = "车辆信息",required = true)
    private ViolationsCarInfo violationsCarInfo;

    @ApiModelProperty(value = "人员信息",required = true)
    private ViolationsPersonInfo violationsPersonInfo;

    @ApiModelProperty(value = "处理人员",required = true)
    private String dealPerson;

    @ApiModelProperty(value = "任务下发时间",required = true)
    private Date issuedTime;

    @ApiModelProperty(value = "处理完成事件",required = true)
    private Date completionTime;

    @ApiModelProperty(value = "事件描述",required = true)
    private String eventDesc;

    @ApiModelProperty(value = "处理后图片",required = true)
    private String completionUrl;

    @ApiModelProperty(value = "处理结果描述",required = true)
    private String dealDesc;

    @ApiModelProperty(value = "是否真实， 1：真实事件 2：误报事件",required = true)
    private Integer isReal;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCatchImageUrl() {
        return catchImageUrl;
    }

    public void setCatchImageUrl(String catchImageUrl) {
        this.catchImageUrl = catchImageUrl;
    }

    public ViolationsCarInfo getViolationsCarInfo() {
        return violationsCarInfo;
    }

    public void setViolationsCarInfo(ViolationsCarInfo violationsCarInfo) {
        this.violationsCarInfo = violationsCarInfo;
    }

    public ViolationsPersonInfo getViolationsPersonInfo() {
        return violationsPersonInfo;
    }

    public void setViolationsPersonInfo(ViolationsPersonInfo violationsPersonInfo) {
        this.violationsPersonInfo = violationsPersonInfo;
    }

    public String getDealPerson() {
        return dealPerson;
    }

    public void setDealPerson(String dealPerson) {
        this.dealPerson = dealPerson;
    }

    public Date getIssuedTime() {
        return issuedTime;
    }

    public void setIssuedTime(Date issuedTime) {
        this.issuedTime = issuedTime;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getCompletionUrl() {
        return completionUrl;
    }

    public void setCompletionUrl(String completionUrl) {
        this.completionUrl = completionUrl;
    }

    public String getDealDesc() {
        return dealDesc;
    }

    public void setDealDesc(String dealDesc) {
        this.dealDesc = dealDesc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsReal() {
        return isReal;
    }

    public void setIsReal(Integer isReal) {
        this.isReal = isReal;
    }
}
