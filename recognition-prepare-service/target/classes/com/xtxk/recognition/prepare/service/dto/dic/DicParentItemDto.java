package com.xtxk.recognition.prepare.service.dto.dic;

import io.swagger.annotations.ApiModelProperty;

public class DicParentItemDto {

    @ApiModelProperty(value ="id", required = true)
    private Integer id;

    @ApiModelProperty(value ="字典code", required = true)
    private String dicCode;

    @ApiModelProperty(value ="字典名称", required = true)
    private String dicName;

//    private List<DicChildItemDto> dicChildItemDtos;

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

//    public List<DicChildItemDto> getDicChildItemDtos() {
//        return dicChildItemDtos;
//    }
//
//    public void setDicChildItemDtos(List<DicChildItemDto> dicChildItemDtos) {
//        this.dicChildItemDtos = dicChildItemDtos;
//    }
}
