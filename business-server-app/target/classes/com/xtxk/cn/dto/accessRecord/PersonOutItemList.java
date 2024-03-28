package com.xtxk.cn.dto.accessRecord;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PersonOutItemList {
    @ApiModelProperty(value = "数量")
    private Integer outCount;
    @ApiModelProperty(value = "人员数据")
    private List<PersonTimeSlot> personOutList;
}
