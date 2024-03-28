package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AreaAlarmCountRespDto {

    @ApiModelProperty(value = "区域告警数量列表")
    private List<AreaItemDto> areaItemDtos;

    public List<AreaItemDto> getAreaItemDtos() {
        return areaItemDtos;
    }

    public void setAreaItemDtos(List<AreaItemDto> areaItemDtos) {
        this.areaItemDtos = areaItemDtos;
    }
}
