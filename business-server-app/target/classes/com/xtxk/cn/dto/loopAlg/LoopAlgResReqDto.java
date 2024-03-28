package com.xtxk.cn.dto.loopAlg;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class LoopAlgResReqDto {

    private String loopId;

    @NotBlank(message = "算法code不能够为空.")
    @ApiModelProperty(value = "算法code")
    private String algCode;

    @NotBlank(message = "轮询顺序不能够为空.")
    @ApiModelProperty(value = "轮询顺序")
    private Integer algOrder;


    @NotBlank(message = "轮询资源不能够为空.")
    @ApiModelProperty(value = "轮询资源Id")
    private List<String> resourceIds;


    @NotBlank(message = "轮询时间不能够为空.")
    @ApiModelProperty(value = "轮询时间")
    private Integer loopTime;


    @NotBlank(message = "是否启用不能够为空.")
    @ApiModelProperty(value = "是否启用")
    private Boolean isEnable;
}