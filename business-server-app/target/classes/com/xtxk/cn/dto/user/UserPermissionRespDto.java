package com.xtxk.cn.dto.user;

import io.swagger.annotations.ApiModelProperty;

public class UserPermissionRespDto {

    @ApiModelProperty(value = "权限",required = true)
    private String[] permission;

    public String[] getPermission() {
        return permission;
    }

    public void setPermission(String[] permission) {
        this.permission = permission;
    }
}
