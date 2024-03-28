package com.xtxk.cn.dto.algorithmicModel;

import io.swagger.annotations.ApiModelProperty;

public class AlgorithmicModeListItemDto {

    @ApiModelProperty(value = "算法模型id", required = true)
    private Integer id;

    @ApiModelProperty(value = "算法名称", required = true)
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
