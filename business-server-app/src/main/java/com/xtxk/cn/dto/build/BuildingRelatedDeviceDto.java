package com.xtxk.cn.dto.build;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BuildingRelatedDeviceDto {
    @ApiModelProperty(value = "楼栋id")
    private String resourceId;
    @ApiModelProperty(value = "楼栋名称")
    private String resourceName;
    @ApiModelProperty(value = "楼栋经度")
    private BigDecimal longitude;
    @ApiModelProperty(value = "楼栋纬度")
    private BigDecimal latitude;
    @ApiModelProperty(value = "楼栋identityId")
    private String identityId;
    @ApiModelProperty(value = "楼栋类别(building:表示楼栋")
    private String category;
    @ApiModelProperty(value = "楼栋关联设备数量")
    private Integer deviceNum;
    @ApiModelProperty(value = "楼栋是否存在离线设备")
    private Boolean isDeviceOffline;
}
