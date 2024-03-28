package com.xtxk.cn.dto.algorithmicModel;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class DetailAlgorithmicModelReqDto {

    @ApiModelProperty(value = "算法模型id", required = true)
    @NotNull(message = "算法模型id不能为空")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
