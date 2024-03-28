package com.xtxk.cn.dto.geogra;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;


@ApiModel(description = "修改监控设备Dto")
public class UpdateMonitorDeviceReqDto {

    @ApiModelProperty(value = "设备经度",example = "17.336")
    private BigDecimal deviceLongitude;

    @ApiModelProperty(value = "设备纬度",example = "56.333")
    private BigDecimal deviceLatitude;

    @ApiModelProperty(value = "设备高度",example = "15")
    private Double deviceHeight;

    @ApiModelProperty(value = "所属区域",example = "1")
    private String district;

    @ApiModelProperty(value = "详细地址",example = "xx街道xx社区")
    private String address;

    @ApiModelProperty(value = "楼栋id")
    private Integer buildingId;

    @ApiModelProperty(value = "组织机构id")
    private String directoryId;

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
}
