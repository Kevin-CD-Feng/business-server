package com.xtxk.cn.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class DeviceVideoRecord implements Serializable {

    private String deviceId;

    private String deviceName;

    private String deviceSipId;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer enable;

}