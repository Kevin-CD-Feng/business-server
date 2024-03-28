package com.xtxk.cn.dto.monitor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;

@Data
public class ResInfoForMapRspDto {
    @ApiModelProperty(value = "资源id",required = true)
    private String resourceId;
    @ApiModelProperty(value = "资源名称",required = true)
    private String resourceName;

    @ApiModelProperty(value = "资源经度",required = true)
    private BigDecimal longitude;
    @ApiModelProperty(value = "资源纬度",required = true)
    private BigDecimal latitude;
    @ApiModelProperty(value = "上线状态(online,上线,offline,下线)",required = true)
    private String deviceState;
    @ApiModelProperty(value = "资源sipId",required = true)
    private String resourceSipId;
    @ApiModelProperty(value = "资源类别(device:表示设备，gate:表示门禁,building:表示楼栋",required = true)
    private String category;

    private Integer devState;
}
