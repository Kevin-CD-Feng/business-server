package com.xtxk.cn.dto.AlarmPolicyConfiguration;

import java.util.List;

public class ListAlarmPolicyConfigurationRespDto {

    private List<AlarmPolicyConfigurationItemDto> alarmPolicyConfigurationItemDtoList;

    public List<AlarmPolicyConfigurationItemDto> getAlarmPolicyConfigurationItemDtoList() {
        return alarmPolicyConfigurationItemDtoList;
    }

    public void setAlarmPolicyConfigurationItemDtoList(List<AlarmPolicyConfigurationItemDto> alarmPolicyConfigurationItemDtoList) {
        this.alarmPolicyConfigurationItemDtoList = alarmPolicyConfigurationItemDtoList;
    }
}
