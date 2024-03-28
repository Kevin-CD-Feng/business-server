package com.xtxk.cn.service.impl.alarm;

import com.xtxk.cn.mapper.AlarmDealInfoMapper;
import com.xtxk.cn.service.alarm.AlarmDealInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: lost
 * @description: alarmDealInfo业务实现层
 * @date: 2022-10-10 14:02:36
 */
@Service
public class AlarmDealInfoServiceImpl implements AlarmDealInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmDealInfoServiceImpl.class);

    @Autowired
    private AlarmDealInfoMapper alarmDealInfoMapper;
}