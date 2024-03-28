package com.xtxk.cn.dto.monitor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeviceAndGateStateRspDto {
    @ApiModelProperty(value = "设备id")
    private String equipId;
    @ApiModelProperty(value = "设备状态,0表示上线，1表示下线")
    private Integer equipState;
    @ApiModelProperty(value = "设备类型:device表示摄像头，gate表示门禁")
    private String type;
}