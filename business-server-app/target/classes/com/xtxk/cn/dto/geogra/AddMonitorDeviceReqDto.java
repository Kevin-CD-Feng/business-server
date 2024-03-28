package com.xtxk.cn.dto.geogra;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * AddMonitorDeviceReqDto
 *
 * @author chenzhi
 * @date 2022/10/18 9:09
 * @description
 */
@ApiModel(description = "新增监控设备Dto")
public class AddMonitorDeviceReqDto {

    @NotBlank(message = "设备id不能为空")
    @ApiModelProperty(value = "设备id",example = "1")
    private String deviceId;

    @NotBlank(message = "设备名称不能为空")
    @ApiModelProperty(value = "设备名称",example = "海康摄像头")
    private String deviceName;

    @NotBlank(message = "设备sipId不能为空")
    @ApiModelProperty(value = "设备sipId",example = "海康摄像头")
    private String deviceSipId;

    @ApiModelProperty(value = "设备型号",example = "XXX-AAA")
    private String deviceModel;

    @ApiModelProperty(value = "设备状态（0:在线 1:离线）",example = "0")
    private Integer deviceState;

    @ApiModelProperty(value = "设备厂商",example = "海康")
    private String deviceManufacturer;

   // @NotBlank(message = "设备ip不能为空")
    @ApiModelProperty(value = "设备ip",example = "172.1.1.1")
    private String deviceIp;

    @NotNull(message = "设备端口不能为空")
    @ApiModelProperty(value = "设备端口",example = "3006")
    private Integer devicePort;

    @NotBlank(message = "所属节点不能为空")
    @ApiModelProperty(value = "所属节点",example = "节点1")
    private String deviceNode;

    @NotNull(message = "设备经度不能为空")
    @ApiModelProperty(value = "设备经度",example = "17.336")
    private BigDecimal deviceLongitude;

    @NotNull(message = "设备纬度不能为空")
    @ApiModelProperty(value = "设备纬度",example = "56.333")
    private BigDecimal deviceLatitude;

    @NotNull(message = "设备高度不能为空")
    @ApiModelProperty(value = "设备高度",example = "15")
    private Double deviceHeight;

    @NotBlank(message = "所属区域不能为空")
    @ApiModelProperty(value = "所属区域",example = "1")
    private String district;

    @NotBlank(message = "详细地址不能为空")
    @ApiModelProperty(value = "详细地址",example = "xx街道xx社区")
    private String address;

    @ApiModelProperty(value = "组织机构id",example = "99d785669c7e4bdbb32543440ddc506d")
    private String directoryId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public Integer getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(Integer deviceState) {
        this.deviceState = deviceState;
    }

    public String getDeviceManufacturer() {
        return deviceManufacturer;
    }

    public void setDeviceManufacturer(String deviceManufacturer) {
        this.deviceManufacturer = deviceManufacturer;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public Integer getDevicePort() {
        return devicePort;
    }

    public void setDevicePort(Integer devicePort) {
        this.devicePort = devicePort;
    }

    public String getDeviceNode() {
        return deviceNode;
    }

    public void setDeviceNode(String deviceNode) {
        this.deviceNode = deviceNode;
    }

    public BigDecimal getDeviceLongitude() {
        return deviceLongitude;
    }

    public void setDeviceLongitude(BigDecimal deviceLongitude) {
        this.deviceLongitude = deviceLongitude;
    }

    public BigDecimal getDeviceLatitude() {
        return deviceLatitude;
    }

    public void setDeviceLatitude(BigDecimal deviceLatitude) {
        this.deviceLatitude = deviceLatitude;
    }

    public Double getDeviceHeight() {
        return deviceHeight;
    }

    public void setDeviceHeight(Double deviceHeight) {
        this.deviceHeight = deviceHeight;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeviceSipId() {
        return deviceSipId;
    }

    public void setDeviceSipId(String deviceSipId) {
        this.deviceSipId = deviceSipId;
    }

    public String getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(String directoryId) {
        this.directoryId = directoryId;
    }
}
