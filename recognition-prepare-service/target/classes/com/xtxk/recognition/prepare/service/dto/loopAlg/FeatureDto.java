package com.xtxk.recognition.prepare.service.dto.loopAlg;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FeatureDto {
    @ApiModelProperty(value = "人员id")
    private Integer personId;

    @ApiModelProperty(value = "人员特征值")
    private String feature;
}