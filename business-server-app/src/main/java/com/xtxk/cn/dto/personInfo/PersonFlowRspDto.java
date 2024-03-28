package com.xtxk.cn.dto.personInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class PersonFlowRspDto {

    @ApiModelProperty(value = "人总流出量")
    private Integer totalOut;

    @ApiModelProperty(value = "人总流入量")
    private Integer totalIn;

    @ApiModelProperty(value = "人流量数据")
    List<PersonFlowVo> data;
}