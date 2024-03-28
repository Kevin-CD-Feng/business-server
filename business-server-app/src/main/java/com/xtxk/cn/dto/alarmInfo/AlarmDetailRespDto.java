package com.xtxk.cn.dto.alarmInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(description = "告警详情返回")
public class AlarmDetailRespDto {

    @ApiModelProperty(value = "id",required = true)
    private Integer id;

    @ApiModelProperty(value = "告警来源")
    private String resourceName;

    @ApiModelProperty(value = "告警事件")
    private String eventName;

    @ApiModelProperty(value = "告警事件Code")
    private String eventCode;

    @ApiModelProperty(value = "告警时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date catchTime;

    @ApiModelProperty(value = "事件状态")
    private Integer status;

    @ApiModelProperty(value = "事件状态名称")
    private String statusName;

    @ApiModelProperty(value = "抓拍图片")
    private String catchImageUrl;

    @ApiModelProperty(value = "处理人员")
    private String dealPerson;

    @ApiModelProperty(value = "任务下发时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date issuedTime;

    @ApiModelProperty(value = "处理完成事件")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date completionTime;

    @ApiModelProperty(value = "事件描述")
    private String eventDesc;

    @ApiModelProperty(value = "处理后图片")
    private String completionUrl;

    @ApiModelProperty(value = "处理结果描述")
    private String dealDesc;

    @ApiModelProperty(value = "违规类型（1：违规车辆;2：违规人员;3:违规楼栋）", required = true)
    private Integer violationsType;

    @ApiModelProperty(value = "是否需实时处置(0:非实时处理；1：实时处理)", required = true)
    private Integer processFlag;

    @ApiModelProperty(value = "违规区域")
    private String vioArea;

    @ApiModelProperty(value = "处理结果描述")
    private List<AlarmDealInfoRspDto> deals;

    @ApiModelProperty(value = "违规对象信息")
    private AlarmObjectDto targetObject;

    @ApiModelProperty(value = "告警类型")
    private String alarmType;

    @ApiModelProperty(value = "录像文件url")
    private String recordUrl;

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getCatchTime() {
        return catchTime;
    }

    public void setCatchTime(Date catchTime) {
        this.catchTime = catchTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCatchImageUrl() {
        return catchImageUrl;
    }

    public void setCatchImageUrl(String catchImageUrl) {
        this.catchImageUrl = catchImageUrl;
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

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public Integer getViolationsType() {
        return violationsType;
    }

    public void setViolationsType(Integer violationsType) {
        this.violationsType = violationsType;
    }

    public Integer getProcessFlag() {
        return processFlag;
    }

    public void setProcessFlag(Integer processFlag) {
        this.processFlag = processFlag;
    }

    public List<AlarmDealInfoRspDto> getDeals() {
        return deals;
    }

    public void setDeals(List<AlarmDealInfoRspDto> deals) {
        this.deals = deals;
    }

    public AlarmObjectDto getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(AlarmObjectDto targetObject) {
        this.targetObject = targetObject;
    }

    public String getVioArea() {
        return vioArea;
    }

    public void setVioArea(String vioArea) {
        this.vioArea = vioArea;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }
}
