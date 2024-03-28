package com.xtxk.cn.dto.accessRecord;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccessCarInRecordRespDto {
    private Integer ID;
    private Integer InType;
    private LocalDateTime InTime;
}
