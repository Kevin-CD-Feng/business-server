package com.xtxk.cn.dto.monitor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BuildingWithDeviceRspDto {
    @ApiModelProperty(value = "设备id",required = true)
    private String deviceId;
    @ApiModelProperty(value = "楼栋ids",required = true)
    private Integer buildingId;
}