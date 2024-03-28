package com.xtxk.cn.dto.EvenalgorithmicBinding;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class DeleteAlgorithmicBindingReqDto {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Integer[] ids;

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }
}
