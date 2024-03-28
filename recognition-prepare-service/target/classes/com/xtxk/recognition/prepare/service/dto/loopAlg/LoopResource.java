package com.xtxk.recognition.prepare.service.dto.loopAlg;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoopResource {
    @ApiModelProperty(value = "资源id")
    private String resourceId;
    @ApiModelProperty(value = "资源名称")
    private String resourceName;
}