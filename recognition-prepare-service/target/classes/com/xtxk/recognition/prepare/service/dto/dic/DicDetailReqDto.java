package com.xtxk.recognition.prepare.service.dto.dic;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class DicDetailReqDto {

    /**
     * 字典id
     */
    @ApiModelProperty(value = "字典id", required = true)
    @NotNull(message = "字典id不能为空")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
