package com.xtxk.recognition.prepare.service.dto.dic;

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
}