package com.xtxk.cn.service.alarm;

import com.xtxk.cn.dto.alarmInfo.DealAlarmInfoReqDto;
import com.xtxk.cn.entity.AlarmInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AlarmInfoManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmInfoManager.class);

    @Autowired
    private AlarmInfoService alarmInfoService;


    public AlarmInfo dealAlarmInfo(DealAlarmInfoReqDto dealAlarmInfoReqDto) throws Exception {
       AlarmInfo info = alarmInfoService.dealAlarmInfo(dealAlarmInfoReqDto);
       return info;
    }
}
