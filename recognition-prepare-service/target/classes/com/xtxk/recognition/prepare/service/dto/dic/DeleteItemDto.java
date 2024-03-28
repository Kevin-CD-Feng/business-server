package com.xtxk.recognition.prepare.service.dto.dic;

import io.swagger.annotations.ApiModelProperty;

public class DeleteItemDto {

    @ApiModelProperty(value = "字典项Code集合", required = true)
    private String[] itemCodes;

    public String[] getItemCodes() {
        return itemCodes;
    }

    public void setItemCodes(String[] itemCodes) {
        this.itemCodes = itemCodes;
    }
}
