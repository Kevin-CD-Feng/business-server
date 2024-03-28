package com.xtxk.cn.third.entity.house;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @author HW
 */
@Data
public class OilHouseHold implements Serializable {

    private String HOUSEHOLD_INF_ID;

    private String HOUSE_ID;

    private String OWNERS_NAME;

    private String ID_NUMBER;

    private String WORK_UNIT;

    private String HOMEOWNER_SEX;

    private Date RECORD_TIME;

    private String CURRENT_STATE;

    private Date WORKING_DATE;

    private String JOB_LEVEL;

    private String TECHNICAL_LEVEL;

    private String PERSON_CLASSIFI;

    private String CONTACT_NUM;

    private Date RELIEVE_CONTRACT_DATE;

    private Date RETIREMENT_DATE;

    private String MARITAL_STATUS;

    private String OTHER_SITUATIONS;

    private String SPOUSES_NAME;

    private String TRANSACTION_ID;

    private Date LASTTIME;

    private String MOVE_TYPE;

    private Date MOVE_IN_TIME;

    private Date MOVE_OUT_TIME;

    private Date SHOULD_MOVE_OUT_TIME;

    private String HOUSE_CODE;

    private String HOMEOWNER_SIMP_SPE;

    private String HOMEOWNER_FULL_PINYIN;

    private String PERSON_CATE_CODE;

    private String PERSON_CATE_NAME;

    private String HOUSE_USE_CODE;

    private String HOUSE_USE_NAME;

    private String JOB_STATE_CODE;

    private String JOB_STATE_NAME;

    private String SPOUSES_ID_NUMBER;

    private String CO_OWNER_RELA;

    private String REMARKS;

    private Date CREATE_DATE;

    private Date UPDATE_DATE;

    private String DATA_REGION;

    private Integer BSFLAG;

}