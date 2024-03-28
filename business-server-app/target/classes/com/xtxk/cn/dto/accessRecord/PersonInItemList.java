package com.xtxk.cn.dto.accessRecord;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PersonInItemList {
    @ApiModelProperty(value = "数量")
    private Integer inCount;
    @ApiModelProperty(value = "人员数据")
    private List<PersonTimeSlot> personInList;
}
