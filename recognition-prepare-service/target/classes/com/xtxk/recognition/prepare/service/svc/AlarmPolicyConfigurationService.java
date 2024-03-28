package com.xtxk.recognition.prepare.service.svc;


import com.xtxk.recognition.prepare.service.dto.AlarmPolicyConfiguration.*;

import java.util.List;


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

    /**
     * 查询已配置策略设备列表
     * @return
     */
    List<ListAlarmPolicyDeviceDto> getAlarmPolicyDeviceList(String deviceName);
}