package com.xtxk.cn.controller.alarm;

import com.xtxk.cn.service.alarm.AlarmDealInfoService;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = {"模块"}, description = "管理")
@RestController
@RequestMapping("/alarmDealInfo")
public class AlarmDealInfoController {


    @Autowired
    private AlarmDealInfoService alarmDealInfoService;


}