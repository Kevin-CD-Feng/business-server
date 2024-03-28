package com.xtxk.cn.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xtxk.cn.dto.geogra.DeviceStateConvert;
import com.xtxk.cn.dto.personInfo.DistrictConvert;
import com.xtxk.cn.enums.DeviceStateEnum;
import com.xtxk.cn.enums.DistrictEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * MonitorDevicePO
 *
 * @author chenzhi
 * @date 2022/10/17 9:33
 * @description
 */
@ApiModel(description = "监控设备PO")
public class MonitorDevicePO {

    @ExcelProperty(value = "设备id", index = 0)
    @ApiModelProperty(value = "设备id", example = "1")
    private String deviceId;

    @ExcelProperty(value = "设备名称", index = 1)
    @ColumnWidth(value = 15)
    @ApiModelProperty(value = "设备名称", example = "海康摄像头")
    private String deviceName;

    @ExcelIgnore
    private String deviceSipId;

    @ExcelProperty(value = "设备型号", index = 2)
    @ApiModelProperty(value = "设备型号", example = "XXX-AAA")
    @ColumnWidth(value = 15)
    private String deviceModel;

    @ExcelProperty(value = "设备状态", index = 3, converter = DeviceStateConvert.class)
    @ApiModelProperty(value = "设备状态（0:在线 1:离线）", example = "0")
    @ColumnWidth(value = 15)
    private Integer deviceState;

    @ExcelIgnore
    @ApiModelProperty(value = "设备状态名称", example = "离线")
    private String deviceStateName;

    @ExcelProperty(value = "设备厂商", index = 4)
    @ApiModelProperty(value = "设备厂商", example = "海康")
    @ColumnWidth(value = 15)
    private String deviceManufacturer;

    @ExcelProperty(value = "设备ip", index = 5)
    @ApiModelProperty(value = "设备ip", example = "172.1.1.1")
    private String deviceIp;

    @ExcelProperty(value = "设备端口", index = 6)
    @ApiModelProperty(value = "设备端口", example = "3006")
    @ColumnWidth(value = 15)
    private Integer devicePort;

    @ExcelProperty(value = "所属节点", index = 7)
    @ApiModelProperty(value = "所属节点", example = "节点1")
    @ColumnWidth(value = 15)
    private String deviceNode;

    @ExcelProperty(value = "设备经度", index = 8)
    @ApiModelProperty(value = "设备经度", example = "17.336")
    @ColumnWidth(value = 15)
    private BigDecimal deviceLongitude;

    @ExcelProperty(value = "设备纬度", index = 9)
    @ApiModelProperty(value = "设备纬度", example = "56.333")
    @ColumnWidth(value = 15)
    private BigDecimal deviceLatitude;

    @ExcelProperty(value = "设备高度", index = 10)
    @ApiModelProperty(value = "设备高度", example = "15.59")
    @ColumnWidth(value = 15)
    private Double deviceHeight;

    @ExcelIgnore
    @ApiModelProperty(value = "所属区域", example = "1")
    private String district;


    @ExcelProperty(value = "所属区域", index = 11)
    @ApiModelProperty(value = "区域名称", example = "一区")
    @ColumnWidth(value = 15)
    private String districtName;

    @ExcelProperty(value = "详细地址", index = 12)
    @ColumnWidth(value = 20)
    @ApiModelProperty(value = "详细地址", example = "xx街道xx社区")
    private String address;

    @ExcelProperty(value = "创建时间", index = 13)
    @ColumnWidth(value = 18)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", example = "2022-10-17 10:00:00")
    private Date createTime;

    @ExcelProperty(value = "更新时间", index = 14)
    @ColumnWidth(value = 18)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间", example = "2022-10-17 10:00:00")
    private Date updateTime;

    @ApiModelProperty(value = "楼栋id")
    @ExcelIgnore
    private Integer buildingId;

    @ApiModelProperty(value = "组织机构id")
    @ExcelIgnore
    private String directoryId;

    @ApiModelProperty(value = "高空抛物设备监控楼栋id")
    @ExcelIgnore
    private Integer monitorBuildingId;

    @ApiModelProperty(value = "海康cameraCode")
    @ExcelIgnore
    private String hkCameraCode;

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

    public String getDeviceStateName() {
        return DeviceStateEnum.queryDescByCode(getDeviceState());
    }

    public void setDeviceStateName(String deviceStateName) {
        this.deviceStateName = deviceStateName;
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

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getDeviceSipId() {
        return deviceSipId;
    }

    public void setDeviceSipId(String deviceSipId) {
        this.deviceSipId = deviceSipId;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public String getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(String directoryId) {
        this.directoryId = directoryId;
    }

    public Integer getMonitorBuildingId() {
        return monitorBuildingId;
    }

    public void setMonitorBuildingId(Integer monitorBuildingId) {
        this.monitorBuildingId = monitorBuildingId;
    }

    public String getHkCameraCode() {
        return hkCameraCode;
    }

    public void setHkCameraCode(String hkCameraCode) {
        this.hkCameraCode = hkCameraCode;
    }
}
