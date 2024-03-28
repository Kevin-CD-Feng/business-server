package com.xtxk.cn.third.entity.house;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * @author Administrator
 */
public class OilHouseInformation implements Serializable {

    private static final long serialVersionUID = 3281419296271715686L;
    /***
     *房屋ID
     */
    private String HOUSE_ID;
    /***
     *小区ID
     */
    private String VILLAGE_ID;
    /***
     *楼宇ID
     */
    private String BUILDING_ID;
    /***
     *房屋编号
     */
    private String HOUSE_CODE;
    /***
     *门牌号
     */
    private String HOUSE_NAME;
    /***
     *所属单元
     */
    private String HOUSE_UNIT;
    /***
     *所在楼层
     */
    private String HOUSE_FLOOR;
    /***
     *房屋户型
     */
    private String HOUSE_TYPE;
    /***
     *C4房屋户型
     */
    private String C4HOUSE_TYPE;
    /***
     *房屋间数
     */
    private Double HOUSE_NUMBER;
    /***
     *阳台面积
     */
    private String BALCONY_AREA;
    /***
     *阳台是否封闭
     */
    private String BALCONY_CLOSE;
    /***
     *封闭阳台
     */
    private String BALCONY_CLOSE_AREA;
    /***
     *房屋使用性质
     */
    private String HOUSE_NATURE;
    /***
     *房屋状态
     */
    private String HOUSE_STATE;
    /***
     *房屋朝向
     */
    private String HOUSE_DIREC;
    /***
     *是否成套
     */
    private String COMPLETE_SET;
    /***
     *建筑面积
     */
    private BigDecimal BUILT_UP_AREA;
    /***
     *使用面积
     */
    private BigDecimal USE_AREA;
    /***
     *分摊系数
     */
    private String SHARE_COEFFICIENT;
    /***
     *分摊面积
     */
    private String SHARE_AREA;
    /***
     *阁楼面积
     */
    private String ATTIC_AREA;
    /***
     *其他情况
     */
    private String OTHER_SITUATIONS;
    /***
     *当前状态
     */
    private String CURRENT_STATE;
    /***
     *备注
     */
    private String REMARKS;
    /***
     *油田标识
     */
    private String DATA_REGION;
    /***
     *删除标识
     */
    private Integer BSFLAG;

    /***
     * 业务转换楼栋id
     */
    private Integer buildId;

    public String getHOUSE_ID() {
        return HOUSE_ID;
    }

    public void setHOUSE_ID(String HOUSE_ID) {
        this.HOUSE_ID = HOUSE_ID;
    }

    public String getVILLAGE_ID() {
        return VILLAGE_ID;
    }

    public void setVILLAGE_ID(String VILLAGE_ID) {
        this.VILLAGE_ID = VILLAGE_ID;
    }

    public String getBUILDING_ID() {
        return BUILDING_ID;
    }

    public void setBUILDING_ID(String BUILDING_ID) {
        this.BUILDING_ID = BUILDING_ID;
    }

    public String getHOUSE_CODE() {
        return HOUSE_CODE;
    }

    public void setHOUSE_CODE(String HOUSE_CODE) {
        this.HOUSE_CODE = HOUSE_CODE;
    }

    public String getHOUSE_NAME() {
        return HOUSE_NAME;
    }

    public void setHOUSE_NAME(String HOUSE_NAME) {
        this.HOUSE_NAME = HOUSE_NAME;
    }

    public String getHOUSE_UNIT() {
        return HOUSE_UNIT;
    }

    public void setHOUSE_UNIT(String HOUSE_UNIT) {
        this.HOUSE_UNIT = HOUSE_UNIT;
    }

    public String getHOUSE_FLOOR() {
        return HOUSE_FLOOR;
    }

    public void setHOUSE_FLOOR(String HOUSE_FLOOR) {
        this.HOUSE_FLOOR = HOUSE_FLOOR;
    }

    public String getHOUSE_TYPE() {
        return HOUSE_TYPE;
    }

    public void setHOUSE_TYPE(String HOUSE_TYPE) {
        this.HOUSE_TYPE = HOUSE_TYPE;
    }

    public String getC4HOUSE_TYPE() {
        return C4HOUSE_TYPE;
    }

    public void setC4HOUSE_TYPE(String c4HOUSE_TYPE) {
        C4HOUSE_TYPE = c4HOUSE_TYPE;
    }

    public Double getHOUSE_NUMBER() {
        return HOUSE_NUMBER;
    }

    public void setHOUSE_NUMBER(Double HOUSE_NUMBER) {
        this.HOUSE_NUMBER = HOUSE_NUMBER;
    }

    public String getBALCONY_AREA() {
        return BALCONY_AREA;
    }

    public void setBALCONY_AREA(String BALCONY_AREA) {
        this.BALCONY_AREA = BALCONY_AREA;
    }

    public String getBALCONY_CLOSE() {
        return BALCONY_CLOSE;
    }

    public void setBALCONY_CLOSE(String BALCONY_CLOSE) {
        this.BALCONY_CLOSE = BALCONY_CLOSE;
    }

    public String getBALCONY_CLOSE_AREA() {
        return BALCONY_CLOSE_AREA;
    }

    public void setBALCONY_CLOSE_AREA(String BALCONY_CLOSE_AREA) {
        this.BALCONY_CLOSE_AREA = BALCONY_CLOSE_AREA;
    }

    public String getHOUSE_NATURE() {
        return HOUSE_NATURE;
    }

    public void setHOUSE_NATURE(String HOUSE_NATURE) {
        this.HOUSE_NATURE = HOUSE_NATURE;
    }

    public String getHOUSE_STATE() {
        return HOUSE_STATE;
    }

    public void setHOUSE_STATE(String HOUSE_STATE) {
        this.HOUSE_STATE = HOUSE_STATE;
    }

    public String getHOUSE_DIREC() {
        return HOUSE_DIREC;
    }

    public void setHOUSE_DIREC(String HOUSE_DIREC) {
        this.HOUSE_DIREC = HOUSE_DIREC;
    }

    public String getCOMPLETE_SET() {
        return COMPLETE_SET;
    }

    public void setCOMPLETE_SET(String COMPLETE_SET) {
        this.COMPLETE_SET = COMPLETE_SET;
    }

    public BigDecimal getBUILT_UP_AREA() {
        return BUILT_UP_AREA;
    }

    public void setBUILT_UP_AREA(BigDecimal BUILT_UP_AREA) {
        this.BUILT_UP_AREA = BUILT_UP_AREA;
    }

    public BigDecimal getUSE_AREA() {
        return USE_AREA;
    }

    public void setUSE_AREA(BigDecimal USE_AREA) {
        this.USE_AREA = USE_AREA;
    }

    public String getSHARE_COEFFICIENT() {
        return SHARE_COEFFICIENT;
    }

    public void setSHARE_COEFFICIENT(String SHARE_COEFFICIENT) {
        this.SHARE_COEFFICIENT = SHARE_COEFFICIENT;
    }

    public String getSHARE_AREA() {
        return SHARE_AREA;
    }

    public void setSHARE_AREA(String SHARE_AREA) {
        this.SHARE_AREA = SHARE_AREA;
    }

    public String getATTIC_AREA() {
        return ATTIC_AREA;
    }

    public void setATTIC_AREA(String ATTIC_AREA) {
        this.ATTIC_AREA = ATTIC_AREA;
    }

    public String getOTHER_SITUATIONS() {
        return OTHER_SITUATIONS;
    }

    public void setOTHER_SITUATIONS(String OTHER_SITUATIONS) {
        this.OTHER_SITUATIONS = OTHER_SITUATIONS;
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

    public Integer getBuildId() {
        return buildId;
    }

    public void setBuildId(Integer buildId) {
        this.buildId = buildId;
    }
}