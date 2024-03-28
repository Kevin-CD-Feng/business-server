package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AlarmCountRespDto {
    @ApiModelProperty(value = "告警数量列表")
    private List<AlarmCountItemDto> alarmCountItemDtos;

    public List<AlarmCountItemDto> getAlarmCountItemDtos() {
        return alarmCountItemDtos;
    }

    public void setAlarmCountItemDtos(List<AlarmCountItemDto> alarmCountItemDtos) {
        this.alarmCountItemDtos = alarmCountItemDtos;
    }
}
