package com.xtxk.cn.dto.user;

import com.xtxk.cn.utils.valid.ListValues;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdatePermissionReqDto {

    @ApiModelProperty(value = "账户类型",required = true)
    @NotNull
    private Integer accountType;

    @ApiModelProperty(value = "id",required = true)
    @NotBlank
    private String id;

    @ApiModelProperty(value = "权限",required = true)
    private String[] permissionType;

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String[] permissionType) {
        this.permissionType = permissionType;
    }
}
