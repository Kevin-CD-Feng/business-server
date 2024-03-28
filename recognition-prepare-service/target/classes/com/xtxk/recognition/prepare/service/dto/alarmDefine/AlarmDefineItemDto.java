package com.xtxk.recognition.prepare.service.dto.alarmDefine;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

public class AlarmDefineItemDto {

    /**
     * 告警id
     */
    @ApiModelProperty(value = "告警id", required = true)
    @NotNull(message = "告警id不能为空")
    private Integer id;

    /**
     * 告警类型
     */
    @ApiModelProperty(value = "告警类型", required = true)
    @NotNull(message = "告警类型不能为空")
    private Integer typeCode;

    @ApiModelProperty(value = "告警事件", required = true)
    private String eventName;

    /**
     * 告警事件
     */
    @ApiModelProperty(value = "告警事件", required = true)
    @NotNull(message = "告警事件不能为空")
    private Integer eventCode;


    /**
     * 处置
     */
    @ApiModelProperty(value = "处置（0：非实时处置；1：实时处置）", required = true)
    @NotNull(message = "处置不能为空")
    private Integer disposalFlag;

    /**
     * 检测频率
     */
    @ApiModelProperty(value = "检测频率", required = true)
    @NotNull(message = "检测频率")
    private Integer intervals;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public Integer getEventCode() {
        return eventCode;
    }

    public void setEventCode(Integer eventCode) {
        this.eventCode = eventCode;
    }

    public Integer getDisposalFlag() {
        return disposalFlag;
    }

    public void setDisposalFlag(Integer disposalFlag) {
        this.disposalFlag = disposalFlag;
    }

    public Integer getIntervals() {
        return intervals;
    }

    public void setIntervals(Integer intervals) {
        this.intervals = intervals;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
