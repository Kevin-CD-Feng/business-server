package com.xtxk.cn.dto.AlarmPolicyConfiguration;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AddAlarmPolicyConfigurationReqDto {

    @ApiModelProperty(value = "设备名称", required = true)
    private String name;

    @ApiModelProperty(value = "设备-resourceId", required = true)
    private String resourceId;

    private List<AlarmPolicyConfigurationItemDto> alarmPolicyConfigurationItemDtoList;

    public List<AlarmPolicyConfigurationItemDto> getAlarmPolicyConfigurationItemDtoList() {
        return alarmPolicyConfigurationItemDtoList;
    }

    public void setAlarmPolicyConfigurationItemDtoList(List<AlarmPolicyConfigurationItemDto> alarmPolicyConfigurationItemDtoList) {
        this.alarmPolicyConfigurationItemDtoList = alarmPolicyConfigurationItemDtoList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
