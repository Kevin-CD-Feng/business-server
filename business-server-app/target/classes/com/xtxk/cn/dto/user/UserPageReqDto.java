package com.xtxk.cn.dto.user;

import com.xtxk.cn.common.PageParam;
import com.xtxk.cn.utils.valid.ListValues;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserPageReqDto extends PageParam {

    @ApiModelProperty(value = "用户类型",required = true)
    @NotBlank
    private Integer accountType;

    @ApiModelProperty(value = "关键字",required = true)
    private String key;

    @ApiModelProperty(value = "权限类型",required = true)
    @NotNull
    @ListValues(arrayValue = {-1,1,2}, message = "权限类型不能为空")
    private Integer permissionType;

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }
}
