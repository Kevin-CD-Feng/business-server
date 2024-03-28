package com.xtxk.cn.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class HouseInfo implements Serializable {

    private static final long serialVersionUID = -7443526473704604959L;

    private Integer houseId;

    private Integer buildId;

    private String houseCode;

    private String houseNature;

    private Double houseArea;

    private String contacts;

    private String telphone;

    private Date startTime;

    private Date endTime;

    private String lakeHouseId;

    private Date createTime;

    private Date updateTime;

}