package com.xtxk.cn.dto.accessRecord;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AccessPersonRecordReqDto {
    @ApiModelProperty(value = "门户中心生产环境Token")
    private String jwt;
    @ApiModelProperty(value = "访问操作类型")
    private String operate;
    @ApiModelProperty(value = "访问服务key")
    private String customKey;
    @ApiModelProperty(value = "区域central:塔里木基地，frontLine:前线作业区")
    private String area;
    @ApiModelProperty(value = "时效，默认当天")
    private String timeQuantum;
    @ApiModelProperty(value = "查询条件 JSONArray对象")
    private List<QueryParamItem> queryParam;

}
