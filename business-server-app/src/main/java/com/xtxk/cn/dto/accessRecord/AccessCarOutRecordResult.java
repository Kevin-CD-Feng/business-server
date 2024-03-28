package com.xtxk.cn.dto.accessRecord;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AccessCarOutRecordResult {
    @ApiModelProperty(value = "记录总条数")
    private Integer total;
    @ApiModelProperty(value = "车辆流出数据")
    private List<CarTimeSlot> carOutList;
}
