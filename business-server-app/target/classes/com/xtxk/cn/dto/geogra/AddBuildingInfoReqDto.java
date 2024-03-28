package com.xtxk.cn.dto.geogra;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * AddBuildingInfoReqDto
 *
 * @author chenzhi
 * @date 2022/10/18 11:20
 * @description
 */
@ApiModel(description = "新增楼栋建筑Dto")
public class AddBuildingInfoReqDto {

    @NotBlank(message = "楼栋名称不能为空")
    @ApiModelProperty(value = "楼栋名称",example = "1号楼")
    private String buildingName;

    @NotBlank(message = "楼栋管理员名称不能为空")
    @ApiModelProperty(value = "楼栋管理员名称",example = "张三")
    private String buildingManagerName;

    @NotBlank(message = "楼栋管理员电话不能为空")
    @ApiModelProperty(value = "楼栋管理员电话",example = "15963635986")
    private String buildingManagerPhone;

    @NotNull(message = "所属区域不能为空")
    @ApiModelProperty(value = "所属区域",example = "1")
    private String district;

    @NotNull(message = "楼栋类型不能为空")
    @ApiModelProperty(value = "楼栋类型",example = "1")
    private String buildingType;

    @NotNull(message = "楼栋门户数不能为空")
    @ApiModelProperty(value = "楼栋门户数",example = "1000")
    private Integer buildingDoorCount;

    @NotNull(message = "楼栋人口数不能为空")
    @ApiModelProperty(value = "楼栋人口数",example = "10000")
    private Integer buildingPersonCount;

    @NotNull(message = "楼栋楼层不能为空")
    @ApiModelProperty(value = "楼栋楼层",example = "19")
    private Integer buildingFloorCount;

    @NotNull(message = "占地面积不能为空")
    @ApiModelProperty(value = "占地面积",example = "4569")
    private Integer floorSpace;

    @NotNull(message = "楼栋经度不能为空")
    @ApiModelProperty(value = "楼栋经度",example = "17.369")
    private String buildingLongitude;

    @NotNull(message = "楼栋纬度不能为空")
    @ApiModelProperty(value = "楼栋纬度",example = "98.365")
    private String buildingLatitude;

    @NotBlank(message = "详细地址不能为空")
    @ApiModelProperty(value = "详细地址",example = "xx街道xx社区")
    private String address;

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
}
