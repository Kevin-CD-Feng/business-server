package com.xtxk.cn.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class GroupDevice implements Serializable {

    private static final long serialVersionUID = 1055230681869275596L;

    private String groupId;

    private String strategyId;

    private String deviceId;

    private String deviceName;

    private String resCh;

    private String resourceType;

    private Integer deviceState;

}