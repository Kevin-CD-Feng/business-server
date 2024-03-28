package com.xtxk.cn.dto.dic;

import io.swagger.annotations.ApiModelProperty;

public class DicItemDto {
    @ApiModelProperty(value = "字典项id", required = true)
    private Integer id;

    @ApiModelProperty(value = "字典项Code", required = true)
    private String itemCode;

    @ApiModelProperty(value = "字典项名称", required = true)
    private String itemName;

    @ApiModelProperty(value = "字典项描述", required = true)
    private String itemDesc;

    @ApiModelProperty(value = "字典项父Code", required = true)
    private String itemParentCode;

    @ApiModelProperty(value = "是否启用（0:启用 1:停用）", required = true)
    private Integer enable;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getItemParentCode() {
        return itemParentCode;
    }

    public void setItemParentCode(String itemParentCode) {
        this.itemParentCode = itemParentCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
