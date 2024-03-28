package com.xtxk.cn.service.alarm;


import com.xtxk.cn.dto.AlarmPolicyConfiguration.AddAlarmPolicyConfigurationReqDto;
import com.xtxk.cn.dto.AlarmPolicyConfiguration.ConfigurationListDto;
import com.xtxk.cn.dto.AlarmPolicyConfiguration.ListAlarmPolicyConfigurationReqDto;
import com.xtxk.cn.dto.AlarmPolicyConfiguration.ListAlarmPolicyConfigurationRespDto;

/**
 * @author: lost
 * @description: AlarmPolicyConfiguration数据层
 * @date: 2022-10-10 14:02:36
 */
public interface AlarmPolicyConfigurationService {

    /**
     * 新增
     * @param addAlarmPolicyConfigurationReqDto
     */
    void addAndUpdate(AddAlarmPolicyConfigurationReqDto addAlarmPolicyConfigurationReqDto);

    /**
     * 根据resourceId查询绑定事件列表
     * @param listAlarmPolicyConfigurationReqDto
     * @return
     */
    ListAlarmPolicyConfigurationRespDto list(ListAlarmPolicyConfigurationReqDto listAlarmPolicyConfigurationReqDto);


    /**
     * 查询全部策略配置信息
     * @return
     */
    ConfigurationListDto configurationList();
}