package com.xtxk.cn.dto.accessRecord;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CarTimeSlot {
    @ApiModelProperty(value = "时间段")
    private String timeSlot;
    @ApiModelProperty(value = "当前数量")
    private Integer curCount;
}
