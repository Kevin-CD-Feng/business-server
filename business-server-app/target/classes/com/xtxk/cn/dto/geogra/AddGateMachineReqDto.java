package com.xtxk.cn.dto.geogra;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * AddGateMachineReqDto
 *
 * @author chenzhi
 * @date 2022/10/18 14:28
 * @description
 */
@ApiModel(description = "新增门禁闸机Dto")
public class AddGateMachineReqDto {

    @NotBlank(message = "闸机名称不能为空")
    @ApiModelProperty(value = "闸机名称",example = "门禁闸机1")
    private String gateMachineName;

    @NotBlank(message = "值班亭电话不能为空")
    @ApiModelProperty(value = "值班亭电话",example = "15963636365")
    private String dutyPhone;

    @NotNull(message = "所属区域不能为空")
    @ApiModelProperty(value = "所属区域",example = "1")
    private String district;

    @NotNull(message = "闸机经度不能为空")
    @ApiModelProperty(value = "闸机经度",example = "17.326")
    private BigDecimal gateMachineLongitude;

    @NotNull(message = "闸机纬度不能为空")
    @ApiModelProperty(value = "闸机纬度",example = "56.3659")
    private BigDecimal gateMachineLatitude;

    @NotBlank(message = "详细地址不能为空")
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
