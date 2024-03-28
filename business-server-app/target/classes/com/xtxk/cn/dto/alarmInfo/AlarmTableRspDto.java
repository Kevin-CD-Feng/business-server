package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AlarmTableRspDto {
    @ApiModelProperty(name = "catchTime",value = "违规时间")
    private String catchTime;
    @ApiModelProperty(name = "dtos",value = "各个违规事件")
    private List<AlarmTableDto> dtos;
}