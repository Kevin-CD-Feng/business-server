package com.xtxk.recognition.prepare.service.dto.dic;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class DictionaryEntryReqDto {

    @ApiModelProperty(value = "字典元素父编号", required = true)
    @NotNull(message = "字典元素父编号不能为空")
    private String dicParentCode;

    public String getDicParentCode() {
        return dicParentCode;
    }

    public void setDicParentCode(String dicParentCode) {
        this.dicParentCode = dicParentCode;
    }
}
