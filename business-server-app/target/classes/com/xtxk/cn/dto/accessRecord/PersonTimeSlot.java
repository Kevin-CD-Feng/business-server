package com.xtxk.cn.dto.accessRecord;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PersonTimeSlot {
    @ApiModelProperty(value = "时间段")
    private String timeSlot;
    @ApiModelProperty(value = "当前数量")
    private Integer curCount;
    @ApiModelProperty(value = "人员数据")
    @JsonIgnore
    private List<AccessPersonRecordRespDto> persons;
}
