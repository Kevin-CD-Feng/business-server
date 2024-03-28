package com.xtxk.cn.dto.statistic.event;

import com.xtxk.cn.common.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-07 15:21
 */
@Data
public class EventParam extends PageParam implements Serializable {

    private static final long serialVersionUID = 1907885737748845025L;

    @ApiModelProperty("事件来源")
    private Integer eventSource;

    @ApiModelProperty("开始时间")
    private String beginTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("0(周) 1(月) 2(年)")
    private Integer type;

}
