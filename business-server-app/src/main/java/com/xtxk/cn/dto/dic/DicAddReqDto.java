package com.xtxk.cn.dto.dic;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DicAddReqDto {

    @ApiModelProperty(value = "字典Code", required = true)
    private String dicCode;

    @ApiModelProperty(value = "字典名称", required = true)
    private String dicName;

    @ApiModelProperty(value = "字典描述", required = true)
    private String dicDesc;

    @ApiModelProperty(value = "上级字典元素code", required = true)
    private String dicParentCode;

    private List<DicAddItemDto> dicItemDtos;

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

    public String getDicDesc() {
        return dicDesc;
    }

    public void setDicDesc(String dicDesc) {
        this.dicDesc = dicDesc;
    }

    public String getDicParentCode() {
        return dicParentCode;
    }

    public void setDicParentCode(String dicParentCode) {
        this.dicParentCode = dicParentCode;
    }

    public List<DicAddItemDto> getDicItemDtos() {
        return dicItemDtos;
    }

    public void setDicItemDtos(List<DicAddItemDto> dicItemDtos) {
        this.dicItemDtos = dicItemDtos;
    }
}
