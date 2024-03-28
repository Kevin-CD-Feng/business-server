package com.xtxk.recognition.prepare.service.dto.loopAlg;

import lombok.Data;

@Data
public class LoopAlgResVo {

    private String  loopId;

    private String algCode;

    private String eventCode;

    private Integer algOrder;

    private String resourceId;

    private String resourceName;

    private Float loopTime;

    private Boolean isEnable;
}