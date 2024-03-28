package com.xtxk.cn.dto.AlarmPolicyConfiguration;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class ListAlarmPolicyConfigurationReqDto {

    @ApiModelProperty(value = "设备-resourceId", required = true)
    @NotNull(message = "设备-resourceId不能为空")
    private String resourceId;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
