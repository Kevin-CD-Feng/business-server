package com.xtxk.cn.third.entity.user;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */

public class OiPersonnelInformation implements Serializable {

    private static final long serialVersionUID = -7792273163983618780L;

    /***
     * 小区人员ID
     */
    private String PERSON_ID;

    /***
     * 机构ID
     */
    private String ORG_ID;

    /***
     * 姓名
     */
    private String NAME;

    /***
     * 身份证号
     */
    private String ID_NUMBER;

    /***
     * 性别
     */
    private String SEX;

    /***
     * 出生日期
     */
    private Date BIRTHDAY;

    /***
     * 民族
     */
    private String NATION;

    /**
     * 籍贯
     */
    private String NATIVE_PLACE;

    /***
     * 血型
     */
    private String BLOOD_TYPE;

    /***
     * 出生地
     */
    private String BIRTHPLACE;

    /***
     * 姓名简拼
     */
    private String NAME_SPELLING;

    /***
     * 姓名全拼
     */
    private String NAME_FULL_PINYIN;

    /***
     * 注销原因
     */
    private String CANCEL_REASON;

    /***
     * 国家或地区
     */
    private String REGION;

    /***
     * 当前状态
     */
    private String CURRENT_STATE;

    /***
     * 备注
     */
    private String REMARKS;

    /***
     * 油田标识
     */
    private String DATA_REGION;

    /***
     * 删除标识
     */
    private Integer BSFLAG;

    /**
     * 创建时间
     */
    private Date CREATE_DATE;

    /***
     * 更新日期
     */
    private Date UPDATE_DATE;

    public String getPERSON_ID() {
        return PERSON_ID;
    }

    public void setPERSON_ID(String PERSON_ID) {
        this.PERSON_ID = PERSON_ID;
    }

    public String getORG_ID() {
        return ORG_ID;
    }

    public void setORG_ID(String ORG_ID) {
        this.ORG_ID = ORG_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getID_NUMBER() {
        return ID_NUMBER;
    }

    public void setID_NUMBER(String ID_NUMBER) {
        this.ID_NUMBER = ID_NUMBER;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public Date getBIRTHDAY() {
        return BIRTHDAY;
    }

    public void setBIRTHDAY(Date BIRTHDAY) {
        this.BIRTHDAY = BIRTHDAY;
    }

    public String getNATION() {
        return NATION;
    }

    public void setNATION(String NATION) {
        this.NATION = NATION;
    }

    public String getNATIVE_PLACE() {
        return NATIVE_PLACE;
    }

    public void setNATIVE_PLACE(String NATIVE_PLACE) {
        this.NATIVE_PLACE = NATIVE_PLACE;
    }

    public String getBLOOD_TYPE() {
        return BLOOD_TYPE;
    }

    public void setBLOOD_TYPE(String BLOOD_TYPE) {
        this.BLOOD_TYPE = BLOOD_TYPE;
    }

    public String getBIRTHPLACE() {
        return BIRTHPLACE;
    }

    public void setBIRTHPLACE(String BIRTHPLACE) {
        this.BIRTHPLACE = BIRTHPLACE;
    }

    public String getNAME_SPELLING() {
        return NAME_SPELLING;
    }

    public void setNAME_SPELLING(String NAME_SPELLING) {
        this.NAME_SPELLING = NAME_SPELLING;
    }

    public String getNAME_FULL_PINYIN() {
        return NAME_FULL_PINYIN;
    }

    public void setNAME_FULL_PINYIN(String NAME_FULL_PINYIN) {
        this.NAME_FULL_PINYIN = NAME_FULL_PINYIN;
    }

    public String getCANCEL_REASON() {
        return CANCEL_REASON;
    }

    public void setCANCEL_REASON(String CANCEL_REASON) {
        this.CANCEL_REASON = CANCEL_REASON;
    }

    public String getREGION() {
        return REGION;
    }

    public void setREGION(String REGION) {
        this.REGION = REGION;
    }

    public String getCURRENT_STATE() {
        return CURRENT_STATE;
    }

    public void setCURRENT_STATE(String CURRENT_STATE) {
        this.CURRENT_STATE = CURRENT_STATE;
    }

    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }

    public String getDATA_REGION() {
        return DATA_REGION;
    }

    public void setDATA_REGION(String DATA_REGION) {
        this.DATA_REGION = DATA_REGION;
    }

    public Integer getBSFLAG() {
        return BSFLAG;
    }

    public void setBSFLAG(Integer BSFLAG) {
        this.BSFLAG = BSFLAG;
    }

    public Date getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(Date CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public Date getUPDATE_DATE() {
        return UPDATE_DATE;
    }

    public void setUPDATE_DATE(Date UPDATE_DATE) {
        this.UPDATE_DATE = UPDATE_DATE;
    }
}