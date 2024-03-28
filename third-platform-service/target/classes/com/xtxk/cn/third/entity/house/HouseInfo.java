package com.xtxk.cn.third.entity.house;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class HouseInfo implements Serializable {

    private static final long serialVersionUID = -1601460509188617861L;
    /***
     * 出租屋id
     */
    private Integer houseId;
    /***
     * 楼栋id
     */
    private Integer buildId;
    /***
     * 出租屋房号
     */
    private String houseCode;
    /***
     * 出租屋性质
     */
    private String houseNature;
    /***
     * 房屋面积
     */
    private Double houseArea;
    /***
     * 联系人
     */
    private String contacts;
    /***
     * 联系电话
     */
    private String telphone;
    /***
     * 租期开始时间
     */
    private Date startTime;
    /***
     * 租期结束时间
     */
    private Date endTime;
    /***
     * 数据银行房屋id
     */
    private String lakeHouseId;

    private Date createTime;

    private Date updateTime;

}