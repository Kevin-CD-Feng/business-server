package com.xtxk.cn.dto.accessRecord;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccessCarRecordReqDto {
    @ApiModelProperty(value = "查询页码大小")
    private Integer pagesize;
    @ApiModelProperty(value = "查询条件")
    private String where;
}
