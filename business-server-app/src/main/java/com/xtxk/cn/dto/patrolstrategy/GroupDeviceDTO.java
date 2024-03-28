package com.xtxk.cn.dto.patrolstrategy;

import lombok.Data;

import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-25 14:26
 */
@Data
public class GroupDeviceDTO implements Serializable {

    private static final long serialVersionUID = -9019979020236463250L;

    private String groupId;
    private String strategyId;
    private String deviceId;
    private String deviceName;
    private String resCh;
    private String resourceType;
    private String pDeviceId;
    private Integer deviceState;
    private String hkCameraCode;
}
