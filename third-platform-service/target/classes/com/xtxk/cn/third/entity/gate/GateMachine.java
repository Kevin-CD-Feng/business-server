package com.xtxk.cn.third.entity.gate;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 门禁
 * @author Administrator
 */
@Data
public class GateMachine implements Serializable {

    private static final long serialVersionUID = -8977855111157853372L;

    private Integer gateMachineId;

    private String gateMachineName;

    private String dutyPhone;

    private String district;

    private BigDecimal gateMachineLongitude;

    private BigDecimal gateMachineLatitude;

    private String address;

    private Date createTime;

    private Date updateTime;

    private Integer gateState;

    private String sourceId;

}