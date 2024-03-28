package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AlarmDealInfoRspDto {
    @ApiModelProperty(value = "处理的状态 1:未核实;2:未处置;3:处置中;4:已处置")
    private String status;
    @ApiModelProperty(value = "处理描述")
    private String dealDesc;
    @ApiModelProperty(value = "处理人")
    private String dealUserId;
    @ApiModelProperty(value = "处理人名称")
    private String dealUserName;
    @ApiModelProperty(value = "处理时间")
    private Date dealTime;
    @ApiModelProperty(value = "所关联的告警id")
    private String alarmId;
}