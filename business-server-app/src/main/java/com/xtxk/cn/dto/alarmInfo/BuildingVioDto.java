package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BuildingVioDto {
    @ApiModelProperty(value = "楼栋id")
    private Integer buildingId;
    @ApiModelProperty(value = "楼层号")
    private String  floorNumber;
    @ApiModelProperty(value = "房间号")
    private String houseNumber;
}