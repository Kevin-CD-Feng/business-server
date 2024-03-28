package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;

public class PropertyPersonItemDto {

    @ApiModelProperty(value = "人员id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "账户名")
    private String userAccount;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}
