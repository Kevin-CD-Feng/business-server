package com.xtxk.cn.dto.dic;

import io.swagger.annotations.ApiModelProperty;

public class DicChildItemDto {
//    @ApiModelProperty(value = "id", required = true)
    private Integer id;

//    @ApiModelProperty(value = "字典code", required = true)
    private String dicCode;

//    @ApiModelProperty(value = "字典名称", required = true)
    private String dicName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
