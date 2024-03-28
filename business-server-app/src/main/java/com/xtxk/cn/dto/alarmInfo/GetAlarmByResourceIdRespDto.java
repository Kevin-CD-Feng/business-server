package com.xtxk.cn.dto.alarmInfo;

import java.util.List;

public class GetAlarmByResourceIdRespDto {

    private List<AlarmInfoItemDto> alarmInfoItemDtos;

    public List<AlarmInfoItemDto> getAlarmInfoItemDtos() {
        return alarmInfoItemDtos;
    }

    public void setAlarmInfoItemDtos(List<AlarmInfoItemDto> alarmInfoItemDtos) {
        this.alarmInfoItemDtos = alarmInfoItemDtos;
    }
}
