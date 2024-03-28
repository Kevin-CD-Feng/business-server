package com.xtxk.cn.entity;

public class DicItem {

    private Integer id;

    /**
     * 字典id
     */
    private Integer dicId;

    /**
     * 字典项code
     */
    private String itemCode;

    /**
     * 字典项名称
     */
    private String itemName;

    /**
     * 字典项描述
     */
    private String itemDesc;

    /**
     * 上级字典项Code
     */
    private String itemParentCode;

    /**
     * 是否删除
     */
    private Integer deleted;

    /**
     * 是否启动
     */
    private Integer enable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDicId() {
        return dicId;
    }

    public void setDicId(Integer dicId) {
        this.dicId = dicId;
    }

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

    public String getItemParentCode() {
        return itemParentCode;
    }

    public void setItemParentCode(String itemParentCode) {
        this.itemParentCode = itemParentCode;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
