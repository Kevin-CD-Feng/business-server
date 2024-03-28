package com.xtxk.recognition.prepare.service.dto.AlarmPolicyConfiguration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AlarmPolicyDeviceDto {
    @ApiModelProperty(value = "告警事件")
    private String eventName;

    @ApiModelProperty(value = "设备id", example = "1")
    private String deviceId;

    @ApiModelProperty(value = "设备名称", example = "海康摄像头")
    private String deviceName;

    @ApiModelProperty(value = "设备sipId")
    private String deviceSipId;

    @ApiModelProperty(value = "设备型号", example = "XXX-AAA")
    private String deviceModel;

    @ApiModelProperty(value = "设备状态（0:在线 1:离线）", example = "0")
    private Integer deviceState;

    @ApiModelProperty(value = "设备状态名称", example = "离线")
    private String deviceStateName;

    @ApiModelProperty(value = "设备厂商", example = "海康")
    private String deviceManufacturer;

    @ApiModelProperty(value = "设备ip", example = "172.1.1.1")
    private String deviceIp;

    @ApiModelProperty(value = "设备端口", example = "3006")
    private Integer devicePort;

    @ApiModelProperty(value = "所属节点", example = "节点1")
    private String deviceNode;

    @ApiModelProperty(value = "设备经度", example = "17.336")
    private BigDecimal deviceLongitude;

    @ApiModelProperty(value = "设备纬度", example = "56.333")
    private BigDecimal deviceLatitude;

    @ApiModelProperty(value = "设备高度", example = "15.59")
    private Double deviceHeight;

    @ApiModelProperty(value = "所属区域", example = "1")
    private String district;


    @ApiModelProperty(value = "区域名称", example = "一区")
    private String districtName;

    @ApiModelProperty(value = "详细地址", example = "xx街道xx社区")
    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", example = "2022-10-17 10:00:00")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间", example = "2022-10-17 10:00:00")
    private Date updateTime;

    @ApiModelProperty(value = "楼栋id")
    private Integer buildingId;

    @ApiModelProperty(value = "组织机构id")
    private String directoryId;

    @ApiModelProperty(value = "高空抛物设备监控楼栋id")
    private Integer monitorBuildingId;

    @ApiModelProperty(value = "海康cameraCode")
    private String hkCameraCode;
}
