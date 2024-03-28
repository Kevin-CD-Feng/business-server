package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class AreaAlarmCountReqDto {

    @ApiModelProperty(value = "时间类型：0(不局限时间),1（当日）2（当月）3（当年)", required = true)
    @NotNull(message = "时间类型不能为空")
    private int timeType;

    @ApiModelProperty(value = "上报类型（0表示全部 ,1：设备上报，2：人工上报）")
    private Integer reportType;

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }
}
