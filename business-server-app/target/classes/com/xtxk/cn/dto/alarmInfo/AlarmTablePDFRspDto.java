package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AlarmTablePDFRspDto {
    @ApiModelProperty(name = "pdf",value = "pdf路径")
    private String pdf;
}