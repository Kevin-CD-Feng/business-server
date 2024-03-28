package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;

public class AlarmTypeItemDto {

    @ApiModelProperty(value = "告警类型编号", required = true)
    private String alarmCode;

    @ApiModelProperty(value = "告警类型名称", required = true)
    private String alarmName;

    @ApiModelProperty(value = "告警类型数量", required = true)
    private Integer count;

    @ApiModelProperty(value = "告警类型比例", required = true)
    private double radio;

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }
}
