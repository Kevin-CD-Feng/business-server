package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "告警各状态数量")
public class StatisticalCountRespDto {

    @ApiModelProperty(value = "未核实")
    private Integer whs;

    @ApiModelProperty(value = "未处置")
    private Integer wcz;

    @ApiModelProperty(value = "处置中")
    private Integer czz;

    @ApiModelProperty(value = "已处置")
    private Integer ycz;

    @ApiModelProperty(value = "核实有效")
    private Integer hsyx;

    @ApiModelProperty(value = "核实无效")
    private Integer hswx;
}
