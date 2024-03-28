package com.xtxk.cn.third.dto.house;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-31 11:29
 */
@Data
public class MockHouse implements Serializable {

    private static final long serialVersionUID = 957899689307794344L;

    @ApiModelProperty(value = "区域湖房屋id")
    private String houseId;

    @ApiModelProperty(value = "房屋编号")
    private String houseCode;

    @ApiModelProperty(value = "楼栋id")
    private String buildId;

    @ApiModelProperty(value = "房屋使用性质")
    private String houseNature;

    @ApiModelProperty(value = "房屋面积")
    private Double buildUpArea;

}
