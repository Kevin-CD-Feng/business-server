package com.xtxk.cn.dto.build;

import com.xtxk.cn.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-01 11:05
 */
@Data
@ApiModel("楼栋请求数据")
public class HouseParams extends PageParam implements Serializable {

    private static final long serialVersionUID = -8327019414270355570L;

    @ApiModelProperty(value = "区域")
    private String area;

    @ApiModelProperty(value = "楼栋id")
    private String buildId;

    @ApiModelProperty(value = "房屋类型")
    private String houseType;

}
