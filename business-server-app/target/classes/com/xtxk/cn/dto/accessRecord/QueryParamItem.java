package com.xtxk.cn.dto.accessRecord;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//查询条件 JSONArray对象
@Data
public class QueryParamItem {
    @ApiModelProperty(value = "查询字段")
    private String key;
    @ApiModelProperty(value = "条件值")
    private String value;
    @ApiModelProperty(value = "查询运算符")
    private String operator;
}
