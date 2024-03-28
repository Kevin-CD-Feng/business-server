package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PersonCarAlarmItemDto {
    @ApiModelProperty(value = "人员或者车辆Id")
    private String PersonOrCarId;
    @ApiModelProperty(value = "联系方式")
    private String contact;
    @ApiModelProperty(value = "违规次数")
    private Integer vioCnt;
    @ApiModelProperty(value = "人员或者车辆名称")
    private String PersonOrCarName;
}