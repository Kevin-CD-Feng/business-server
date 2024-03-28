package com.xtxk.cn.dto.user;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class DeleteUserReqDto {

    @ApiModelProperty(value = "id",required = true)
    @NotNull
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
