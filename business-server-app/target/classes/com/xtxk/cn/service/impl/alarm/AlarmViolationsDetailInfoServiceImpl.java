package com.xtxk.cn.service.impl.alarm;

import com.xtxk.cn.mapper.AlarmViolationsDetailInfoMapper;
import com.xtxk.cn.service.alarm.AlarmViolationsDetailInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: lost
 * @description: alarmViolationsDetailInfo业务实现层
 * @date: 2022-10-10 14:02:36
 */
@Service
public class AlarmViolationsDetailInfoServiceImpl implements AlarmViolationsDetailInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmViolationsDetailInfoServiceImpl.class);

    @Autowired
    private AlarmViolationsDetailInfoMapper alarmViolationsDetailInfoMapper;
}