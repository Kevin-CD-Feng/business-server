package com.xtxk.cn.dto.accessRecord;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccessCarOutRecordRespDto {
    private Integer ID;
    private Integer OutType;
    private LocalDateTime OutTime;
}
