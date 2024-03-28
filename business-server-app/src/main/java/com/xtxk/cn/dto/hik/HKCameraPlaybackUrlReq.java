package com.xtxk.cn.dto.hik;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HKCameraPlaybackUrlReq {
    @ApiModelProperty(value = "设备id")
    private String deviceId;
    @ApiModelProperty(value = "取流协议类型(rtsp,rtmp)")
    private String protocol;
    @ApiModelProperty(value = "存储类型(0:中心存储,1:设备存储)")
    private String recordLocation;
    @ApiModelProperty(value = "开始日期")
    String beginDate;
    @ApiModelProperty(value = "开始时间")
    String beginTime;
    @ApiModelProperty(value = "结束日期")
    String endDate;
    @ApiModelProperty(value = "结束时间")
    String endTime;
}
