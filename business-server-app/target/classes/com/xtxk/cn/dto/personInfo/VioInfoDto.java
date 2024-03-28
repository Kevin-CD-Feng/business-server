package com.xtxk.cn.dto.personInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class VioInfoDto {
    @ApiModelProperty(value = "告警事件Code")
    private String eventCode;
    @ApiModelProperty(value = "告警事件名称")
    private String eventCodeDesc;
    @ApiModelProperty(value = "告警类型code")
    private String algCode;
    @ApiModelProperty(value = "告警类型名称")
    private String algCodeDesc;
    @ApiModelProperty(value = "告警时间",hidden = true)
    private Date catchTime;
    @ApiModelProperty(value = "告警时间")
    private String catchTimeStr;
}