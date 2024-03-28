package com.xtxk.cn.dto.user;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class UserItemDto {

    @ApiModelProperty(value = "id",required = true)
    private Integer id;

    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    @ApiModelProperty(value = "账号名",required = true)
    private String accountName;

    @ApiModelProperty(value = "联系电话",required = true)
    private String phone;

    @ApiModelProperty(value = "权限类型",required = true)
    private Integer[] permission;

    @ApiModelProperty(value = "创建时间",required = true)
    private Date createTime;

    private Integer accountType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer[] getPermission() {
        return permission;
    }

    public void setPermission(Integer[] permission) {
        this.permission = permission;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
