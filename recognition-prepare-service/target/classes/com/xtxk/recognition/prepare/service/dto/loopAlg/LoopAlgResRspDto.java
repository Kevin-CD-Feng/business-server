package com.xtxk.recognition.prepare.service.dto.loopAlg;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LoopAlgResRspDto {

    private String  loopId;

    @ApiModelProperty(value = "算法code")
    private String algCode;

    @ApiModelProperty(value = "事件code")
    private String eventCode;

    @ApiModelProperty(value = "轮询顺序")
    private Integer algOrder;


    @ApiModelProperty(value = "轮询资源Id")
    private String resourceIds;


    @ApiModelProperty(value = "轮询时间")
    private Float loopTime;


    @ApiModelProperty(value = "是否启用")
    private Boolean isEnable;

    @ApiModelProperty(value = "轮询资源")
    private List<LoopResource> resources;
}