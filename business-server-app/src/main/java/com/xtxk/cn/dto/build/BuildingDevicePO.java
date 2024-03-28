package com.xtxk.cn.dto.build;

import lombok.Data;

@Data
public class BuildingDevicePO {
    private Integer buildingId;
    private String buildingName;
    private String buildingIdentity;
    private String deviceId;
    private String deviceName;
    private Integer deviceState;
}
