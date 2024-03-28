package com.xtxk.cn.dto.alarmInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "分页详情数据")
public class PageListItemDto {

    @ApiModelProperty(value = "告警id", required = true)
    private Integer id;

    @ApiModelProperty(value = "告警事件", required = true)
    private String eventName;

    private String resourceId;

    @ApiModelProperty(value = "告警来源", required = true)
    private String resourceName;

    @ApiModelProperty(value = "区域名称", required = true)
    private String areaName;

    @ApiModelProperty(value = "告警时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date alarmTime;

    @ApiModelProperty(value = "是否需实时处置（0:非实时处理；1：实时处理）",example = "1")
    private Integer processFlag;

    @ApiModelProperty(value = "事件状态（1:未核实;2:未处置;3:处置中;4:已处置）",example = "2")
    private Integer status;

    @ApiModelProperty(value = "抓拍图片）")
    private String catchImageUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Integer getProcessFlag() {
        return processFlag;
    }

    public void setProcessFlag(Integer processFlag) {
        this.processFlag = processFlag;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCatchImageUrl() {
        return catchImageUrl;
    }

    public void setCatchImageUrl(String catchImageUrl) {
        this.catchImageUrl = catchImageUrl;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
