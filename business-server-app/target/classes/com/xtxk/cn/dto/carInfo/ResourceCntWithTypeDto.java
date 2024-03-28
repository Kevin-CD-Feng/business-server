package com.xtxk.cn.dto.carInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResourceCntWithTypeDto {
    @ApiModelProperty(value = "数量")
    private Integer cnt;
    @ApiModelProperty(value = "类型")
    private String category;
    @ApiModelProperty(value = "类型别名")
    private String categoryDesc;
}