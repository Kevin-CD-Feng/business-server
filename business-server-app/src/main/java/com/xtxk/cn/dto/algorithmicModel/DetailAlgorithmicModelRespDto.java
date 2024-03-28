package com.xtxk.cn.dto.algorithmicModel;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DetailAlgorithmicModelRespDto {

    @ApiModelProperty(value = "算法模型id", required = true)
    private Integer id;

    @ApiModelProperty(value = "算法code", required = true)
    private String nameCode;

    @ApiModelProperty(value = "算法模型url", required = true)
    private String url;

    @ApiModelProperty(value = "参数", required = true)
    private String parameterSample;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameCode() {
        return nameCode;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParameterSample() {
        return parameterSample;
    }

    public void setParameterSample(String parameterSample) {
        this.parameterSample = parameterSample;
    }
}
