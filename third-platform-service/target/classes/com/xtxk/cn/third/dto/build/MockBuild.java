package com.xtxk.cn.third.dto.build;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-29 20:04
 */
@Data
public class MockBuild implements Serializable {
    private static final long serialVersionUID = 7106886828857762555L;

    @ApiModelProperty(value = "楼宇名称")
    private String buildName;
    @ApiModelProperty(value = "楼宇编号")
    private String buildCode;
    @ApiModelProperty(value = "楼栋类型")
    private String buildType;
    @ApiModelProperty(value = "建筑面积")
    private Double buildUpArea;
    @ApiModelProperty(value = "楼宇层数")
    private Integer floorNum;
    @ApiModelProperty(value = "楼宇id")
    private String buildId;

}
