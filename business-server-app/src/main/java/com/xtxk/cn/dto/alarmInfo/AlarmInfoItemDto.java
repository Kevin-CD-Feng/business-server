package com.xtxk.cn.dto.alarmInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class AlarmInfoItemDto {

    @ApiModelProperty(value = "告警来源", required = true)
    private String sourceName;

    @ApiModelProperty(value = "告警事件", required = true)
    private String eventName;

    @ApiModelProperty(value = "告警时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date alarmTime;

    @ApiModelProperty(value = "事件状态", required = true)
    private String status;

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
}
