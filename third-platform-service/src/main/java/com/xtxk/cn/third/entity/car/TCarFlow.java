package com.xtxk.cn.third.entity.car;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 车流量
 * @author Administrator
 */
@Data
public class TCarFlow implements Serializable {

    private static final long serialVersionUID = 7002056307597344359L;

    private String flowId;

    private String direction;

    private String deviceCode;

    private String carNo;

    private Date catchTime;

    private Date createTime;

}