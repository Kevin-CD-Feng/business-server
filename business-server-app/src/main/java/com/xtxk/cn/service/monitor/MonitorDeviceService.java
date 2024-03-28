package com.xtxk.cn.service.monitor;

import com.github.pagehelper.PageInfo;
import com.xtxk.cn.dto.geogra.AddMonitorDeviceReqDto;
import com.xtxk.cn.dto.geogra.UpdateMonitorDeviceReqDto;
import com.xtxk.cn.dto.monitor.DeviceAndGateStateRspDto;
import com.xtxk.cn.entity.MonitorDevicePO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * MonitorDeviceService
 *
 * @author chenzhi
 * @date 2022/10/14 15:01
 * @description
 */
public interface MonitorDeviceService {


    /***
     * 分页查询监控设备列表
     * @param keyWord
     * @param pageNum 当前页
     * @param pageSize 条数
     * @param deviceStateName 设备状态
     * @param districtName 区域
     * @return
     */
    PageInfo<MonitorDevicePO> getMonitorDeviceList(String keyWord, Integer pageNum, Integer pageSize,Integer deviceStateName,String districtName);

    /**
     * 新增
     * @param addMonitorDeviceReqDto
     */
    void addMonitorDevice(AddMonitorDeviceReqDto addMonitorDeviceReqDto);

    /**
     * 更新设备信息
     * @param deviceId
     * @param updateMonitorDeviceReqDto
     */
    void updateMonitorDevice(String deviceId, UpdateMonitorDeviceReqDto updateMonitorDeviceReqDto);

    /**
     * 删除
     * @param deviceId
     */
    void deleteMonitorDevice(String deviceId);

    /**
     * 获取需要导出的监控设备
     * @param keyWord
     * @return
     */
    List<MonitorDevicePO> getExportMonitorDeviceList(String keyWord,Integer deviceStateName,String districtName);

    /**
     * 获取单个监控设备信息
     * @param monitorDeviceId
     * @return
     */
    MonitorDevicePO getMonitorDevice(String monitorDeviceId);

    /**
     * 获取所有监控设备
     * @return
     */
    List<MonitorDevicePO> getAllMonitorDevice();

    /**
     * 获取所有监控设备
     * @return
     */
    List<DeviceAndGateStateRspDto> queryDeviceAndGateStatus();

    void updateMonitorDevice(MonitorDevicePO po);
}
