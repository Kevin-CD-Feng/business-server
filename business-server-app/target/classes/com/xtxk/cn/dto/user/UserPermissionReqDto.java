package com.xtxk.cn.dto.user;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class UserPermissionReqDto {

    @ApiModelProperty(value = "id",required = true)
    @NotBlank
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
