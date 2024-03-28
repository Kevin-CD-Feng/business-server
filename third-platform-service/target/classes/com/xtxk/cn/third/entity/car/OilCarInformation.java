package com.xtxk.cn.third.entity.car;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class OilCarInformation implements Serializable {

    private static final long serialVersionUID = -8380988192254024794L;
    /***
     *车辆信息ID
     */
    private String INFOR_PRIMARY_ID;
    /***
     *用户单位编码
     */
    private String USER_UNIT_CODE;
    /***
     *用户单位名称
     */
    private String USER_UNIT_NAME;
    /***
     *车牌号
     */
    private String VEHICLE_BRAND;
    /***
     *车辆类型
     */
    private String VEHICLE_TYPE;
    /***
     *车辆所有人
     */
    private String VEHICLE_OWNER;
    /***
     *责任人
     */
    private String PERSON_LIABLE;
    /***
     * 身份证号
     */
    private String ID_NUMBER;
    /***
     * 一卡通编号
     */
    private String CARD_NUMBER;
    /***
     *住址
     */
    private String ADDRESS;
    /***
     *信息录入时间
     */
    private Date ENTRY_TIME;
    /***
     *使用性质
     */
    private String USE_NATURE;
    /***
     *品牌型号
     */
    private String BRAND_MODEL;
    /***
     *车辆识别代号
     */
    private String VEHICLE_IDENTI_CODE;
    /***
     *发动机号
     */
    private String ENGINE_NUMBER;
    /***
     *注册日期
     */
    private Date REGISTRATION_DATE;
    /***
     *发证日期
     */
    private Date ISSUE_DATE;
    /***
     *核载人数
     */
    private Float AUTHORIZ_PASS_NAME;
    /***
     *核载重量kg
     */
    private Float NUCLEAR_PAYLOAD;
    /***
     *当前状态
     */
    private String CURRENT_STATE;
    /***
     *结束时间
     */
    private Date LASTTIME;
    /***
     *用户ID
     */
    private String USER_ID;
    /***
     *用户姓名
     */
    private String USER_NAME;
    /***
     *起始有效时间
     */
    private Date START_EFFECTIVE_TIME;
    /***
     *终止有效时间
     */
    private Date END_EFFECTIVE_TIME;
    /***
     *备注
     */
    private String REMARKS;
    /***
     *更新日期
     */
    private Date UPDATE_DATE;
    /***
     *油田标识
     */
    private String DATA_REGION;
    /***
     *删除标识
     */
    private Integer BSFLAG;


}