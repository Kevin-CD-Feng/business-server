package com.xtxk.cn.dto.dic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventDictItemDto {
    @ApiModelProperty(value = "算法code", required = true)
    private String itemParentCode;
    @ApiModelProperty(value = "事件code", required = true)
    private String itemCode;
    @ApiModelProperty(value = "事件名称", required = true)
    private String itemName;
    @ApiModelProperty(value = "算法名称", required = true)
    private String algorithmicName;
}