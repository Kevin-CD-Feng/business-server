package com.xtxk.cn.dto.monitor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DeviceStatusRspDto {
    @ApiModelProperty(value = "在线设备数量")
    private Integer online;
    @ApiModelProperty(value = "在线总数")
    private Integer total;

    @ApiModelProperty(value = "类型:device表示设备,gate表示门禁")
    private String category;
}