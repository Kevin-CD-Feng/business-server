package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AlarmTypeRatioRespDto {
    @ApiModelProperty(value = "告警类型占比列表")
    private List<AlarmTypeItemDto> alarmTypeItemDtos;

    public List<AlarmTypeItemDto> getAlarmTypeItemDtos() {
        return alarmTypeItemDtos;
    }

    public void setAlarmTypeItemDtos(List<AlarmTypeItemDto> alarmTypeItemDtos) {
        this.alarmTypeItemDtos = alarmTypeItemDtos;
    }
}
