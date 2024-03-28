package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description = "违规车辆信息")
public class ViolationsCarInfo {

    @ApiModelProperty(value = "车牌号", required = true)
    private String carNumber;

    @ApiModelProperty(value = "车主", required = true)
    private String carOwner;

    @ApiModelProperty(value = "联系电话", required = true)
    private String phone;

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
