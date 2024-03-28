package com.xtxk.cn.service.impl.monitor;

import com.xtxk.cn.dto.AlarmTypeCount;
import com.xtxk.cn.dto.AlarmTypeCountResult;
import com.xtxk.cn.dto.geogra.MonitorDeviceDto;
import com.xtxk.cn.entity.MonitorDevicePO;
import com.xtxk.cn.mapper.AlarmInfoMapper;
import com.xtxk.cn.mapper.MonitorDeviceMapper;
import com.xtxk.cn.service.impl.dic.DicItemHandler;
import com.xtxk.cn.service.monitor.MonitorCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * MonitorCenterServiceImpl
 *
 * @author chenzhi
 * @date 2022/10/19 14:22
 * @description
 */
@Service
public class MonitorCenterServiceImpl implements MonitorCenterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorCenterServiceImpl.class);

    @Autowired
    private MonitorDeviceMapper monitorDeviceMapper;

    @Autowired
    private AlarmInfoMapper alarmInfoMapper;

    @Autowired
    private DicItemHandler dicItemHandler;

    @Override
    public List<MonitorDeviceDto> getMonitorDeviceList() {
        List<MonitorDeviceDto> list = monitorDeviceMapper.getDistrictDevice();
        if (!CollectionUtils.isEmpty(list)) {
//            Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
            list.forEach(dto -> {
                dto.setDeviceCount(dto.getDeviceList().size());
//                dto.setDistrictName(mapping.get(dto.getDistrict()));
                dto.setDistrictName(dto.getDistrict());
            });
        }
        return list;
    }

    @Override
    public AlarmTypeCountResult getDeviceAlarmTypeCount() {
        AlarmTypeCountResult result = new AlarmTypeCountResult();
        List<AlarmTypeCount> list = alarmInfoMapper.getAlarmTypeCount();
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
            list.forEach(e -> {
                e.setAlarmTypeDesc(mapping.get(e.getAlramTypeCode()));
            });
        }
        Integer deviceTotalCount = monitorDeviceMapper.getTotalCount();
        result.setList(list);
        result.setDeviceTotalCount(deviceTotalCount);
        return result;
    }

    @Override
    public List<MonitorDevicePO> getDeviceListByDirectoryId(String directoryId) {
        List<MonitorDevicePO> deviceList = monitorDeviceMapper.getDeviceListByDirectoryId(directoryId);
        return deviceList;
    }

    public List<MonitorDevicePO> searchDeviceListByName(String deviceName) {
        List<MonitorDevicePO> deviceList = monitorDeviceMapper.searchDeviceListByName(deviceName);
        return deviceList;
    }
}
