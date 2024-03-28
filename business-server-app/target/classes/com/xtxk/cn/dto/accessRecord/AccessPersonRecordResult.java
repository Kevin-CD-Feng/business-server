package com.xtxk.cn.dto.accessRecord;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccessPersonRecordResult {
    @ApiModelProperty(value = "记录总条数")
    private Integer total;
    @ApiModelProperty(value = "查询结果标识")
    private Boolean flag;
    @ApiModelProperty(value = "人员入数据")
    private PersonInItemList personInItemList;
    @ApiModelProperty(value = "人员出数据")
    private PersonOutItemList personOutItemList;
}
