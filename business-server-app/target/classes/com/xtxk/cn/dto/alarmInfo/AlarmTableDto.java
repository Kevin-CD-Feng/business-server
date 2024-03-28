package com.xtxk.cn.dto.alarmInfo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AlarmTableDto implements Serializable {
    @ApiModelProperty(value = "违规数量")
    private Integer cnt;
    @ApiModelProperty(value = "违规事件code")
    private String eventCode;
    @ApiModelProperty(value = "违规事件描述")
    private String eventName;
    @ApiModelProperty(value = "违规时间")
    private String catchTime;
}