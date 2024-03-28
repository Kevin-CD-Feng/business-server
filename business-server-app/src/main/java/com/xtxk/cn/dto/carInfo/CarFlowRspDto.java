package com.xtxk.cn.dto.carInfo;

import com.xtxk.cn.dto.personInfo.PersonFlowVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class CarFlowRspDto {
    @ApiModelProperty(value = "车总流出量")
    private Integer totalOut;

    @ApiModelProperty(value = "车总流入量")
    private Integer totalIn;

    @ApiModelProperty(value = "车流量数据")
    List<CarFlowVo> data;

}