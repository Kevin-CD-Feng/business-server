package com.xtxk.cn.dto.build;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-01 14:28
 */
@Data
public class HouseVo implements Serializable {

    private static final long serialVersionUID = -2570639106001762582L;

    @ApiModelProperty(value = "房屋id")
    @ExcelIgnore
    private String houseId;

    @ApiModelProperty(value = "房号")
    @ExcelProperty(value = "房号",index = 4)
    @ColumnWidth(value = 18)
    private String houseCode;

    @ApiModelProperty(value = "房屋类型")
    @ExcelProperty(value = "房屋类型",index = 5)
    @ColumnWidth(value = 18)
    private String houseNature;

    @ApiModelProperty(value = "房屋面积")
    @ExcelProperty(value = "房屋面积",index = 6)
    @ColumnWidth(value = 18)
    private String houseArea;

    @ApiModelProperty(value = "楼栋id")
    @ExcelIgnore
    private String buildId;

    @ApiModelProperty(value = "楼栋号")
    @ExcelProperty(value = "楼栋号",index = 1)
    @ColumnWidth(value = 18)
    private String buildNumber;

    @ApiModelProperty(value = "所在楼层")
    @ExcelProperty(value = "所在楼层",index = 3)
    @ColumnWidth(value = 18)
    private String buildFloor;

    @ApiModelProperty(value = "楼栋名称")
    @ExcelProperty(value = "告警id",index = 2)
    @ColumnWidth(value = 18)
    private String buildName;

    @ApiModelProperty(value = "楼栋区域")
    @ExcelProperty(value = "所属片区",index = 0)
    @ColumnWidth(value = 18)
    private String buildDistrict;

    @ApiModelProperty(value = "违规次数")
    @ExcelProperty(value = "违规次数",index = 7)
    @ColumnWidth(value = 18)
    private Integer number;


}
