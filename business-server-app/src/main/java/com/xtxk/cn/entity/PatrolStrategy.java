package com.xtxk.cn.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class PatrolStrategy implements Serializable {

    private static final long serialVersionUID = 1518723237164718773L;
    private String strategyId;

    private String strategyName;

    private Integer strategyType;

    private Integer patrolInterval;

    private Integer isOpenVoice;

    private Integer windowLayout;

    private String patrolScreen;

    private Date createTime;

    private Date updateTime;

    private Integer isDel;

    private Integer type;

    private String userId;


}