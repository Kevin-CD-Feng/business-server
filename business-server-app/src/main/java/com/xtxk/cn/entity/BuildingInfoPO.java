package com.xtxk.cn.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xtxk.cn.dto.geogra.BuildingTypeConvert;
import com.xtxk.cn.enums.BuildingTypeEnum;
import com.xtxk.cn.enums.DistrictEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * BuildingInfoPO
 *
 * @author chenzhi
 * @date 2022/10/17 9:51
 * @description
 */
@ApiModel(description = "楼栋信息PO")
public class BuildingInfoPO {
    @ExcelProperty(value = "楼栋id",index = 0)
    @ApiModelProperty(value = "楼栋id",example = "1")
    private Integer buildingId;

    @ExcelProperty(value = "楼栋名称",index = 1)
    @ApiModelProperty(value = "楼栋名称",example = "1号楼")
    private String buildingName;

    @ExcelProperty(value = "楼栋管理员名称",index = 2)
    @ApiModelProperty(value = "楼栋管理员名称",example = "张三")
    private String buildingManagerName;

    @ExcelProperty(value = "楼栋管理员电话",index = 3)
    @ColumnWidth(value = 12)
    @ApiModelProperty(value = "楼栋管理员电话",example = "15963635986")
    private String buildingManagerPhone;

    @ExcelIgnore
    @ApiModelProperty(value = "所属区域",example = "1")
    private String district;

    @ExcelProperty(value = "区域名称",index = 4)
    @ApiModelProperty(value = "区域名称",example = "一区")
    private String districtName;

    @ExcelIgnore
    @ApiModelProperty(value = "楼栋类型",example = "1")
    private String buildingType;

    @ExcelProperty(value = "楼栋类型名称",index = 5)
    @ApiModelProperty(value = "楼栋类型名称",example = "居民楼")
    private String buildingTypeName;

    @ExcelProperty(value = "楼栋门户数",index = 6)
    @ApiModelProperty(value = "楼栋门户数",example = "1000")
    private Integer buildingDoorCount;

    @ExcelProperty(value = "楼栋人口数",index = 7)
    @ApiModelProperty(value = "楼栋人口数",example = "10000")
    private Integer buildingPersonCount;

    @ExcelProperty(value = "楼栋楼层",index = 8)
    @ApiModelProperty(value = "楼栋楼层",example = "19")
    private Integer buildingFloorCount;

    @ExcelProperty(value = "占地面积",index = 9)
    @ApiModelProperty(value = "占地面积",example = "4569")
    private Integer floorSpace;

    @ExcelProperty(value = "楼栋经度",index = 10)
    @ApiModelProperty(value = "楼栋经度",example = "17.369,18.369")
    private String buildingLongitude;

    @ExcelProperty(value = "楼栋纬度",index = 11)
    @ApiModelProperty(value = "楼栋纬度",example = "98.365,102.356")
    private String buildingLatitude;

    @ExcelProperty(value = "详细地址",index = 12)
    @ColumnWidth(value = 20)
    @ApiModelProperty(value = "详细地址",example = "xx街道xx社区")
    private String address;

    @ExcelProperty(value = "创建时间",index = 13)
    @ColumnWidth(value = 18)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间",example = "2022-10-17 10:00:00")
    private Date createTime;

    @ExcelProperty(value = "更新时间",index = 14)
    @ColumnWidth(value = 18)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间",example = "2022-10-17 10:00:00")
    private Date updateTime;

    @ExcelProperty(value = "楼栋起始高度",index = 15)
    @ApiModelProperty(value = "楼栋起始高度",example = "-5,30")
    private String buildingHeight;

    @ApiModelProperty(name = "buildingIdentity",value = "地图楼栋标识",example = "该标识主要是为了在地图上标识")
    private String buildingIdentity;

    @ApiModelProperty(value = "楼栋号",example = "1")
    private String buildingNumber;

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

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

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public String getBuildingTypeName() {
        return buildingTypeName;
    }

    public void setBuildingTypeName(String buildingTypeName) {
        this.buildingTypeName = buildingTypeName;
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

    public String getBuildingHeight() {
        return buildingHeight;
    }

    public void setBuildingHeight(String buildingHeight) {
        this.buildingHeight = buildingHeight;
    }

    public String getBuildingIdentity() {
        return buildingIdentity;
    }

    public void setBuildingIdentity(String buildingIdentity) {
        this.buildingIdentity = buildingIdentity;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }
}
