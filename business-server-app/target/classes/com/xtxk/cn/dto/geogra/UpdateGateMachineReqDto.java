package com.xtxk.cn.dto.geogra;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * UpdateGateMachineReqDto
 *
 * @author chenzhi
 * @date 2022/10/18 14:36
 * @description
 */
@ApiModel(description = "修改门禁闸机Dto")
public class UpdateGateMachineReqDto {

    @ApiModelProperty(value = "闸机名称",example = "门禁闸机1")
    private String gateMachineName;

    @ApiModelProperty(value = "值班亭电话",example = "15963636365")
    private String dutyPhone;

    @ApiModelProperty(value = "所属区域",example = "1")
    private String district;

    @ApiModelProperty(value = "闸机经度",example = "17.326")
    private BigDecimal gateMachineLongitude;

    @ApiModelProperty(value = "闸机纬度",example = "56.3659")
    private BigDecimal gateMachineLatitude;

    @ApiModelProperty(value = "详细地址",example = "xx街道xx社区")
    private String address;

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
}
