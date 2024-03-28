package com.xtxk.cn.dto.loopAlg;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.util.pattern.PathPattern;

@Data
public class LoopAlgResVo {

    private String  loopId;

    private String algCode;

    private Integer algOrder;

    private String resourceId;

    private String resourceName;

    private Integer loopTime;

    private Boolean isEnable;
}