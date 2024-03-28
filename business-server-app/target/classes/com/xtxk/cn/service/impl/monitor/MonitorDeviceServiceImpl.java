package com.xtxk.cn.service.impl.monitor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.xtxk.cn.dto.geogra.AddMonitorDeviceReqDto;
import com.xtxk.cn.dto.geogra.UpdateMonitorDeviceReqDto;
import com.xtxk.cn.dto.monitor.DeviceAndGateStateRspDto;
import com.xtxk.cn.entity.MonitorDevicePO;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.mapper.MonitorDeviceMapper;
import com.xtxk.cn.service.impl.dic.DicItemHandler;
import com.xtxk.cn.service.monitor.MonitorDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Service
public class MonitorDeviceServiceImpl implements MonitorDeviceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorDeviceServiceImpl.class);

    @Autowired
    private MonitorDeviceMapper monitorDeviceMapper;

    @Autowired
    private DicItemHandler dicItemHandler;


    @Override
    public PageInfo<MonitorDevicePO> getMonitorDeviceList(String keyWord, Integer pageNum, Integer pageSize, Integer deviceStateName, String districtName) {
        PageMethod.startPage(pageNum, pageSize);
        List<MonitorDevicePO> list = monitorDeviceMapper.queryByKeyWord(keyWord, deviceStateName, districtName);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
            list.forEach(e -> {
                e.setDistrictName(mapping.get(e.getDistrict()));
            });
        }
        return new PageInfo<>(list);

    }

    @Override
    public void addMonitorDevice(AddMonitorDeviceReqDto addMonitorDeviceReqDto) {
        // 判断设备是否已经存在
        String deviceId = addMonitorDeviceReqDto.getDeviceId();
        MonitorDevicePO mp = monitorDeviceMapper.getById(deviceId);
        if (null != mp) {
            LOGGER.info("该监控设备已经存在，设备ID:{}", deviceId);
            throw new ServiceException(ErrorCode.MONITOR_DEVICE_ALREADY_EXIST);
        }
        MonitorDevicePO po = new MonitorDevicePO();
        BeanUtils.copyProperties(addMonitorDeviceReqDto, po);
        monitorDeviceMapper.add(po);
    }

    @Override
    public void updateMonitorDevice(String deviceId, UpdateMonitorDeviceReqDto updateMonitorDeviceReqDto) {
        MonitorDevicePO po = monitorDeviceMapper.getById(deviceId);
        if (null == po) {
            LOGGER.info("deviceId:{} is not exist", deviceId);
            throw new ServiceException(ErrorCode.DEVICE_ID_NOT_EXIST);
        }

        BeanUtils.copyProperties(updateMonitorDeviceReqDto, po);
        monitorDeviceMapper.update(po);
    }

    @Override
    public void deleteMonitorDevice(String deviceId) {
        MonitorDevicePO po = monitorDeviceMapper.getById(deviceId);
        if (null == po) {
            LOGGER.info("deviceId:{} is not exist", deviceId);
            throw new ServiceException(ErrorCode.DEVICE_ID_NOT_EXIST);
        }
        monitorDeviceMapper.deleteById(deviceId);
    }

    @Override
    public List<MonitorDevicePO> getExportMonitorDeviceList(String keyWord, Integer deviceStateName, String districtName) {
        List<MonitorDevicePO> list = monitorDeviceMapper.queryByKeyWord(keyWord, deviceStateName, districtName);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
            list.forEach(e -> {
                e.setDistrictName(mapping.get(e.getDistrict()));
            });
        }
        return list;
    }

    @Override
    public MonitorDevicePO getMonitorDevice(String monitorDeviceId) {
        MonitorDevicePO po = monitorDeviceMapper.getById(monitorDeviceId);
        Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
        po.setDistrictName(mapping.get(po.getDistrict()));
        return po;
    }

    @Override
    public List<MonitorDevicePO> getAllMonitorDevice() {
        List<MonitorDevicePO> list = monitorDeviceMapper.getAllList();
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
            list.forEach(e -> {
                e.setDistrictName(mapping.get(e.getDistrict()));
            });
        }
        return list;
    }

    @Override
    public List<DeviceAndGateStateRspDto> queryDeviceAndGateStatus() {
        return null;
    }

    @Override
    public void updateMonitorDevice(MonitorDevicePO po) {
        monitorDeviceMapper.update(po);
    }
}
