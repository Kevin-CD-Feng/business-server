package com.xtxk.cn.dto.carInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CarTypeRspDto {

    @ApiModelProperty("汽车类型具体看字典配置")
    private String carType;

    @ApiModelProperty("数量")
    private Integer cnt;

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
}