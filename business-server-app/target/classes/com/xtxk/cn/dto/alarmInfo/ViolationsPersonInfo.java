package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description = "违规人员信息")
public class ViolationsPersonInfo {
    @ApiModelProperty(value = "人员名称", required = true)
    private String name;

    @ApiModelProperty(value = "人员类型", required = true)
    private String type;

    @ApiModelProperty(value = "联系电话", required = true)
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
