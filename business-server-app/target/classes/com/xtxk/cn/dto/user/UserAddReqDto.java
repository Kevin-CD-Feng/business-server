package com.xtxk.cn.dto.user;

import io.swagger.annotations.ApiModelProperty;

public class UserAddReqDto {

    @ApiModelProperty(value = "用户名",required = true)
    private String userName;
    @ApiModelProperty(value = "账户名",required = true)
    private String accountName;
    @ApiModelProperty(value = "联系电话",required = true)
    private String phone;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "账户类型",required = true)
    private Integer accountType;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }
}
