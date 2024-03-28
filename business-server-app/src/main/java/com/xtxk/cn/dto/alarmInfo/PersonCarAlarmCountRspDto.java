package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PersonCarAlarmCountRspDto {

    @ApiModelProperty(value = "人员告警数量列表")
    private List<PersonCarAlarmItemDto> alarmCountItemPerson;

    @ApiModelProperty(value = "告警数量列表")
    private List<PersonCarAlarmItemDto> alarmCountItemCar;

}