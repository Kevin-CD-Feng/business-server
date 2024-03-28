package com.xtxk.cn.dto.dic;

import io.swagger.annotations.ApiModelProperty;

public class DicListReqDto {

    @ApiModelProperty(value = "字典元素名称")
    private String dicName;

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }
}
