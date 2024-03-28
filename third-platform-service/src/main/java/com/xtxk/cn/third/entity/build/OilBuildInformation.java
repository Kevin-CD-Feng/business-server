package com.xtxk.cn.third.entity.build;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class OilBuildInformation implements Serializable {

    private static final long serialVersionUID = 2440446767423908809L;
    /***
     *楼宇ID
     */
    private String BUILD_ID;
    /***
     *小区ID
     */
    private String VILLAGE_ID;
    /***
     *楼宇编号
     */
    private String BUILD_CODE;
    /***
     *楼宇名称
     */
    private String BUILD_NAME;
    /***
     *产权建筑面积
     */
    private BigDecimal PROP_RIGHT_AREA;
    /***
     *建筑面积
     */
    private BigDecimal BUILT_UP_AREA;
    /***
     *楼宇结构
     */
    private String BUILD_STRUCTURE;
    /***
     *楼宇类型
     */
    private String BUILD_TYPE;
    /***
     *楼宇用途
     */
    private String BUILD_PURPOSE;
    /***
     *楼宇来源
     */
    private String BUILD_SOURCE;
    /***
     *单元数
     */
    private Double UNIT_NUM;
    /***
     *层数
     */
    private Double FLOOR_NUM;
    /***
     *竣工日期
     */
    private Date COMPLE_DATE;
    /***
     *电梯数量
     */
    private Double ELEVATOR_NUM;
    /***
     *车库情况
     */
    private String GARAGE_SITUA;
    /***
     *地下室情况
     */
    private String BASEMENT_SITUA;
    /***
     *供热单控改造
     */
    private String HEATING_CONTROL;
    /***
     *功能配套
     */
    private String FUNCTION_MATCH;
    /***
     *建筑投资
     */
    private String CONSTRUC_INVEST;
    /***
     *其他情况
     */
    private String OTHER_SITUAT;
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


}