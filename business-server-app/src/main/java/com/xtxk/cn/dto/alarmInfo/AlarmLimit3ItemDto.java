package com.xtxk.cn.dto.alarmInfo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

public class AlarmLimit3ItemDto {

    @ApiModelProperty(value = "告警来源")
    private String resourceName;

    @ApiModelProperty(value = "告警事件")
    private String eventName;

    @ApiModelProperty(value = "状态名称")
    private String statusName;

    @ApiModelProperty(value = "告警时间")
    private Date catchTime;

    @ApiModelProperty(value = "经度",example = "17.369")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度",example = "17.369")
    private BigDecimal latitude;

    @ApiModelProperty(value = "状态code")
    private Integer statusCode;

    @ApiModelProperty(value = "事件code")
    private String eventCode;

    @ApiModelProperty(value = "告警来源类型（1：设备上报，2：人工上报）")
    private String resourceType;

    @ApiModelProperty(value = "告警来源类型描述")
    private String resourceTypeDesc;

    @ApiModelProperty(value = "来源id")
    private String resourceId;

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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getCatchTime() {
        return catchTime;
    }

    public void setCatchTime(Date catchTime) {
        this.catchTime = catchTime;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceTypeDesc() {
        return resourceTypeDesc;
    }

    public void setResourceTypeDesc(String resourceTypeDesc) {
        this.resourceTypeDesc = resourceTypeDesc;
    }
}
