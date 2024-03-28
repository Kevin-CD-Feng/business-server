package com.xtxk.recognition.prepare.service.dto.algorithmicModel;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class DeleteAlgorithmicModelReqDto {

    /**
     * 算法模型id
     */
    @ApiModelProperty(value = "算法模型id", required = true)
    @NotNull(message = "算法模型id不能为空")
    private Integer[] ids;

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }
}
