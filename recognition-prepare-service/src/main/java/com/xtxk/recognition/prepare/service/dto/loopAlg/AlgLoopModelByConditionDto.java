package com.xtxk.recognition.prepare.service.dto.loopAlg;

import lombok.Data;

@Data
public class AlgLoopModelByConditionDto {
    private String loopId;
    private String algCode;
    private String eventCode;
    private Integer algOrder;
    private Integer loopTime;
    private String resourceId;
    private String resourceName;
    private String resourceSipId;
    private Integer violationInterval;
    private Integer violationDuration;
    private String webCoordinate;
    private Float frameInterval;
}