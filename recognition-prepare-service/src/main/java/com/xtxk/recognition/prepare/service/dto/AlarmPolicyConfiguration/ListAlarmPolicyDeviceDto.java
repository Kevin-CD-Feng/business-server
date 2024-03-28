package com.xtxk.recognition.prepare.service.dto.AlarmPolicyConfiguration;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ListAlarmPolicyDeviceDto {
    @ApiModelProperty(value = "告警事件")
    private String eventName;

    private List<AlarmPolicyDeviceDto> deviceList;

}
