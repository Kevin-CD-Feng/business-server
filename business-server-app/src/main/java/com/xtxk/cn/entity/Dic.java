package com.xtxk.cn.entity;

/**
 * @author: lost
 * @date: 2022-10-10 14:02:35
 * @description: 表名:t_dic,描述:字典表
 */
public class Dic {

    private Integer id;

    /**
     * 字典code
     */
    private String dicCode;

    /**
     * 字典名称
     */
    private String dicName;

    /**
     * 字典描述
     */
    private String dicDesc;

    /**
     * 父字典
     */
    private String dicParentCode;

    /**
     * 是否删除
     */
    private Integer deleted;

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

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
