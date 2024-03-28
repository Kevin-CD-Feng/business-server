package com.xtxk.cn.dto.monitor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MonitorBuildingWithDeviceReqDto {
    @ApiModelProperty(value = "设备id",required = true)
    private String deviceId;
    @ApiModelProperty(value = "楼栋id",required = true)
    private Integer monitorBuildingId;
}