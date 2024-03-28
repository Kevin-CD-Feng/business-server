package com.xtxk.cn.dto.dic;

import io.swagger.annotations.ApiModelProperty;

public class DictionaryEntryItemDto {

    @ApiModelProperty(value = "父字典项Code", required = true)
    private String itemParentCode;

    @ApiModelProperty(value = "父字典项名称", required = true)
    private String itemParentName;

    public String getItemParentCode() {
        return itemParentCode;
    }

    public void setItemParentCode(String itemParentCode) {
        this.itemParentCode = itemParentCode;
    }

    public String getItemParentName() {
        return itemParentName;
    }

    public void setItemParentName(String itemParentName) {
        this.itemParentName = itemParentName;
    }
}
