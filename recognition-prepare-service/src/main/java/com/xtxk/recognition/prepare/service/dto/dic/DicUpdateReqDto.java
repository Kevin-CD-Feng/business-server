package com.xtxk.recognition.prepare.service.dto.dic;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DicUpdateReqDto {

    @ApiModelProperty(value = "字典id", required = true)
    private Integer id;

    @ApiModelProperty(value = "字典名称", required = true)
    private String dicName;

    @ApiModelProperty(value = "字典Code", required = true)
    private String dicCode;

    @ApiModelProperty(value = "字典描述", required = true)
    private String dicDesc;

    @ApiModelProperty(value = "父字典Code", required = true)
    private String dicParentCode;

    @ApiModelProperty(value = "字典项集合", required = true)
    private List<DicItemDto> dicItemDtos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
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

    public List<DicItemDto> getDicItemDtos() {
        return dicItemDtos;
    }

    public void setDicItemDtos(List<DicItemDto> dicItemDtos) {
        this.dicItemDtos = dicItemDtos;
    }
}
