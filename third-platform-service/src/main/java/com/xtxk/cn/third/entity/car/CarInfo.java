package com.xtxk.cn.third.entity.car;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class CarInfo implements Serializable {

    private static final long serialVersionUID = -3470743193196829760L;
    private Integer carId;

    private String carNumber;

    private String carOwnerName;

    private String carOwnerPhone;

    private String carType;

    private Date createTime;

    private Date updateTime;

    private String lakeCarId;

  }