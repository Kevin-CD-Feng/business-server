package com.xtxk.cn.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xtxk.cn.enums.DistrictEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * GateMachinePO
 *
 * @author chenzhi
 * @date 2022/10/17 10:01
 * @description
 */
@ApiModel(description = "闸机信息PO")
public class GateMachinePO {

    @ExcelProperty(value = "闸机id", index = 0)
    @ApiModelProperty(value = "闸机id", example = "1")
    private Integer gateMachineId;

    @ExcelProperty(value = "闸机名称", index = 1)
    @ApiModelProperty(value = "闸机名称", example = "门禁闸机1")
    private String gateMachineName;

    @ExcelProperty(value = "值班亭电话", index = 2)
    @ColumnWidth(value = 12)
    @ApiModelProperty(value = "值班亭电话", example = "15963636365")
    private String dutyPhone;

    @ExcelIgnore
    @ApiModelProperty(value = "所属区域", example = "1")
    private String district;

    @ExcelProperty(value = "区域名称", index = 3)
    @ApiModelProperty(value = "区域名称",example = "一区")
    private String districtName;

    @ExcelProperty(value = "闸机经度", index = 4)
    @ApiModelProperty(value = "闸机经度", example = "17.326")
    private BigDecimal gateMachineLongitude;

    @ExcelProperty(value = "闸机纬度", index = 5)
    @ApiModelProperty(value = "闸机纬度", example = "56.3659")
    private BigDecimal gateMachineLatitude;

    @ExcelProperty(value = "详细地址", index = 6)
    @ColumnWidth(value = 20)
    @ApiModelProperty(value = "详细地址", example = "xx街道xx社区")
    private String address;

    @ExcelProperty(value = "创建时间", index = 7)
    @ColumnWidth(value = 18)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", example = "2022-10-17 10:00:00")
    private Date createTime;

    @ExcelProperty(value = "更新时间", index = 8)
    @ColumnWidth(value = 18)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间", example = "2022-10-17 10:00:00")
    private Date updateTime;


    @ExcelIgnore
    @ApiModelProperty(value = "门禁上下线")
    private Integer gateState;

    public Integer getGateMachineId() {
        return gateMachineId;
    }

    public void setGateMachineId(Integer gateMachineId) {
        this.gateMachineId = gateMachineId;
    }

    public String getGateMachineName() {
        return gateMachineName;
    }

    public void setGateMachineName(String gateMachineName) {
        this.gateMachineName = gateMachineName;
    }

    public String getDutyPhone() {
        return dutyPhone;
    }

    public void setDutyPhone(String dutyPhone) {
        this.dutyPhone = dutyPhone;
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

    public BigDecimal getGateMachineLongitude() {
        return gateMachineLongitude;
    }

    public void setGateMachineLongitude(BigDecimal gateMachineLongitude) {
        this.gateMachineLongitude = gateMachineLongitude;
    }

    public BigDecimal getGateMachineLatitude() {
        return gateMachineLatitude;
    }

    public void setGateMachineLatitude(BigDecimal gateMachineLatitude) {
        this.gateMachineLatitude = gateMachineLatitude;
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

    public Integer getGateState() {
        return gateState;
    }

    public void setGateState(Integer gateState) {
        this.gateState = gateState;
    }
}
