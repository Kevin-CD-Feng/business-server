package com.xtxk.cn.dto.hik;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HKCameraPreviewUrlReq {
    @ApiModelProperty(value = "设备id")
    private String deviceId;
    @ApiModelProperty(value = "取流协议类型(rtsp,rtmp)")
    private String protocol;
}
