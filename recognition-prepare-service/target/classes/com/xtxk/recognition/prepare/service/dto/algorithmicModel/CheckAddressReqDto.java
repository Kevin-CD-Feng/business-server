package com.xtxk.recognition.prepare.service.dto.algorithmicModel;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class CheckAddressReqDto {

    @ApiModelProperty(value = "算法模型url", required = true)
    @NotBlank(message = "算法模型url不能为空")
    private String url;
}
