package com.xtxk.cn.dto.geogra;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * UpdateBuildingInfoReqDto
 *
 * @author chenzhi
 * @date 2022/10/18 13:46
 * @description
 */
@ApiModel(description = "修改楼栋建筑Dto")
public class UpdateBuildingInfoReqDto {

    @ApiModelProperty(value = "楼栋名称",example = "1号楼")
    private String buildingName;

    @ApiModelProperty(value = "楼栋管理员名称",example = "张三")
    private String buildingManagerName;

    @ApiModelProperty(value = "楼栋管理员电话",example = "15963635986")
    private String buildingManagerPhone;

    @ApiModelProperty(value = "所属区域",example = "1")
    private String district;

    @ApiModelProperty(value = "楼栋类型",example = "1")
    private String buildingType;

    @ApiModelProperty(value = "楼栋门户数",example = "1000")
    private Integer buildingDoorCount;

    @ApiModelProperty(value = "楼栋人口数",example = "10000")
    private Integer buildingPersonCount;

    @ApiModelProperty(value = "楼栋楼层",example = "19")
    private Integer buildingFloorCount;

    @ApiModelProperty(value = "占地面积",example = "4569")
    private Integer floorSpace;

    @ApiModelProperty(value = "楼栋经度",example = "17.369")
    private String buildingLongitude;

    @ApiModelProperty(value = "楼栋纬度",example = "98.365")
    private String buildingLatitude;

    @ApiModelProperty(value = "详细地址",example = "xx街道xx社区")
    private String address;

    @ApiModelProperty(name = "buildingIdentity",value = "建筑标识",example = "该标识主要是为了在地图上标识")
    private String buildingIdentity;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingManagerName() {
        return buildingManagerName;
    }

    public void setBuildingManagerName(String buildingManagerName) {
        this.buildingManagerName = buildingManagerName;
    }

    public String getBuildingManagerPhone() {
        return buildingManagerPhone;
    }

    public void setBuildingManagerPhone(String buildingManagerPhone) {
        this.buildingManagerPhone = buildingManagerPhone;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public Integer getBuildingDoorCount() {
        return buildingDoorCount;
    }

    public void setBuildingDoorCount(Integer buildingDoorCount) {
        this.buildingDoorCount = buildingDoorCount;
    }

    public Integer getBuildingPersonCount() {
        return buildingPersonCount;
    }

    public void setBuildingPersonCount(Integer buildingPersonCount) {
        this.buildingPersonCount = buildingPersonCount;
    }

    public Integer getBuildingFloorCount() {
        return buildingFloorCount;
    }

    public void setBuildingFloorCount(Integer buildingFloorCount) {
        this.buildingFloorCount = buildingFloorCount;
    }

    public Integer getFloorSpace() {
        return floorSpace;
    }

    public void setFloorSpace(Integer floorSpace) {
        this.floorSpace = floorSpace;
    }

    public String getBuildingLongitude() {
        return buildingLongitude;
    }

    public void setBuildingLongitude(String buildingLongitude) {
        this.buildingLongitude = buildingLongitude;
    }

    public String getBuildingLatitude() {
        return buildingLatitude;
    }

    public void setBuildingLatitude(String buildingLatitude) {
        this.buildingLatitude = buildingLatitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuildingIdentity() {
        return buildingIdentity;
    }

    public void setBuildingIdentity(String buildingIdentity) {
        this.buildingIdentity = buildingIdentity;
    }
}
