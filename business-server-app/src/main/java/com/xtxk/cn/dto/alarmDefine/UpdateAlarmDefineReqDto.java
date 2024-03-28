package com.xtxk.cn.dto.alarmDefine;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class UpdateAlarmDefineReqDto {
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
    @NotBlank(message = "告警类型不能为空")
    private String typeCode;

    /**
     * 告警事件
     */
    @ApiModelProperty(value = "告警事件", required = true)
    @NotBlank(message = "告警事件不能为空")
    private String eventCode;


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
    @Max(value = 10,message = "最大每秒10帧")
    private BigDecimal intervals;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public Integer getDisposalFlag() {
        return disposalFlag;
    }

    public void setDisposalFlag(Integer disposalFlag) {
        this.disposalFlag = disposalFlag;
    }


    public BigDecimal getIntervals() {
        return intervals;
    }

    public void setIntervals(BigDecimal intervals) {
        this.intervals = intervals;
    }
}
