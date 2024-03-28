package com.xtxk.cn.third.dto.car;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-29 17:41
 */
@Data
@ApiModel(value = "车辆模拟数据")
public class MockCarInfo implements Serializable {

    private static final long serialVersionUID = 4100731844018689565L;

    @ApiModelProperty(value = "车牌号")
    private String cardNumber;
    @ApiModelProperty(value = "车辆所有人")
    private String vehicleOwner;
    @ApiModelProperty(value = "车辆类型")
    private String vehicleType;
    @ApiModelProperty(value = "区域湖车辆唯一id")
    private String carId;

}
