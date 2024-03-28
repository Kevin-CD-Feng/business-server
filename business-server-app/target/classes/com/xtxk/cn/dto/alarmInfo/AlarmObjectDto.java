package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AlarmObjectDto {
    @ApiModelProperty(value = "当天违规次数")
    private Integer curDayViolCnt;
    @ApiModelProperty(value = "累计违规次数")
    private Integer totalVioCnt;

    @ApiModelProperty(value = "违规对象id")
    private String alarmObjId;
    @ApiModelProperty(value = "违规对象姓名")
    private String alarmObjName;

    @ApiModelProperty(value = "所在楼层")
    private String floorNumber;

    @ApiModelProperty(value = "所在房号")
    private String houseNumber;

    @ApiModelProperty(value = "住户类型")
    private String houseManType;

    @ApiModelProperty(value = "车辆类型")
    private String type;

    @ApiModelProperty(value = "联系方式")
    private String contact;

    private String alarmId;
}