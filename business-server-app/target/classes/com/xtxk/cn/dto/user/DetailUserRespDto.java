package com.xtxk.cn.dto.user;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class DetailUserRespDto {

    @ApiModelProperty(value = "id",required = true)
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    @ApiModelProperty(value = "账号名",required = true)
    private String accountName;

    @ApiModelProperty(value = "联系电话",required = true)
    private String phone;

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
}
