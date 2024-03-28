package com.xtxk.cn.service.monitor;

import com.xtxk.cn.dto.AlarmTypeCountResult;
import com.xtxk.cn.dto.geogra.MonitorDeviceDto;
import com.xtxk.cn.entity.MonitorDevicePO;

import java.util.List;

/**
 * MonitorCenterService
 *
 * @author chenzhi
 * @date 2022/10/19 14:22
 * @description
 */
public interface MonitorCenterService {

    /**
     * 获取监控设备列表
     * @return
     */
    List<MonitorDeviceDto> getMonitorDeviceList();

    /**
     * 获取监控设备概况
     * @return
     */
    AlarmTypeCountResult getDeviceAlarmTypeCount();

    /**
     * 根据组织id查询设备列表
     * @param directoryId
     * @return
     */
    List<MonitorDevicePO> getDeviceListByDirectoryId(String directoryId);

    /**
     * 根据设备名称检索设备列表
     *
     * @param deviceName
     * @return
     */
    List<MonitorDevicePO> searchDeviceListByName(String deviceName);

}
