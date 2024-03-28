package com.xtxk.cn.dto.personInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PersonFlowVo {

    @ApiModelProperty(value = "人流方向 out流出,in流入")
    private String direction;

    @ApiModelProperty(value = "时间")
    private String ti;

    @ApiModelProperty(value = "数量")
    private Integer cnt;
}