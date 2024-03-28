package com.xtxk.cn.dto.build;

import lombok.Data;

import java.util.List;

@Data
public class BuildingDeviceDto {
    private String buildingIdentity;
    private Integer buildingId;
    private String buildingName;
    private List<BuildingMonitorDeviceDto> deviceList;
}
