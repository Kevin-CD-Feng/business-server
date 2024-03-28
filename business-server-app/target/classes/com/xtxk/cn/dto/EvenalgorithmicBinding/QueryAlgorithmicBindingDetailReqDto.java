package com.xtxk.cn.dto.EvenalgorithmicBinding;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class QueryAlgorithmicBindingDetailReqDto {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
