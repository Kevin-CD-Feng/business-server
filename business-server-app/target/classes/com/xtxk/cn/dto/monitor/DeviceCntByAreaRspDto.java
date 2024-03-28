package com.xtxk.cn.dto.monitor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeviceCntByAreaRspDto {
    @ApiModelProperty(value = "区域代码")
    private String areaCode;
    @ApiModelProperty(value = "区域名称")
    private String areaName;
    @ApiModelProperty(value = "数量")
    private Integer cnt;
}