package com.xtxk.cn.dto.dic;

import io.swagger.annotations.ApiModelProperty;

public class SuperiorItemDto {
    @ApiModelProperty(value = "字典code", required = true)
    private String dicCode;

    @ApiModelProperty(value = "字典名称", required = true)
    private String dicName;

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }
}
