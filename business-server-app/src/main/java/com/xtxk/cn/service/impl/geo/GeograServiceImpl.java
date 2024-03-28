package com.xtxk.cn.service.impl.geo;

import com.xtxk.cn.dto.alarmInfo.DevHisAlarmCount;
import com.xtxk.cn.dto.geogra.GeograObjDto;
import com.xtxk.cn.entity.AlarmInfo;
import com.xtxk.cn.enums.GeograTypeEnum;
import com.xtxk.cn.mapper.AlarmInfoMapper;
import com.xtxk.cn.mapper.GateMachineMapper;
import com.xtxk.cn.mapper.MonitorDeviceMapper;
import com.xtxk.cn.service.geo.GeograService;
import com.xtxk.cn.service.impl.dic.DicItemHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GeograServiceImpl
 *
 * @author chenzhi
 * @date 2022/10/27 9:58
 * @description
 */
@Service
public class GeograServiceImpl implements GeograService {

    @Autowired
    private MonitorDeviceMapper monitorDeviceMapper;

    @Autowired
    private AlarmInfoMapper alarmInfoMapper;

    @Autowired
    private GateMachineMapper gateMachineMapper;

    @Autowired
    private DicItemHandler dicItemHandler;


    @Override
    public List<GeograObjDto> getGeograObjResult(String type) {
        List<GeograObjDto> result = new ArrayList<>();
        String[] typeArr = type.split(",");
        // 字典映射
        Map<String, String> dicItemCodeMapping = dicItemHandler.getDicItemCodeMapping();
        List<DevHisAlarmCount> hisAlarmCountList = alarmInfoMapper.getDevHisAlarmCount();

        // 设备与历史告警数映射
        Map<String, Long> devHisCountMapping = new HashMap<>();
        if (!CollectionUtils.isEmpty(hisAlarmCountList)) {
            hisAlarmCountList.forEach(e -> {
                String resourceId = e.getResourceId();
                if (StringUtils.isNotBlank(resourceId)) {
                    devHisCountMapping.put(resourceId, e.getHistoryAlarmCount());
                }
            });
        }

        for (String s : typeArr) {
            Integer typeNum = Integer.valueOf(s);
            List<GeograObjDto> list = null;
            if (GeograTypeEnum.DEVICE.getCode().equals(typeNum)) {
                list = monitorDeviceMapper.getGeograObjDto();
                if (!CollectionUtils.isEmpty(list)) {
                    list.forEach(e -> {
                        e.setDistrictName(dicItemCodeMapping.get(e.getDistrict()));
                        e.setHistoryAlarmCount(devHisCountMapping.get(e.getId()));
                        AlarmInfo alarmInfo = e.getAlarmInfo();
                        if (null != alarmInfo) {
                            alarmInfo.setEventName(dicItemCodeMapping.get(alarmInfo.getEvent()));
                            alarmInfo.setAreaName(dicItemCodeMapping.get(alarmInfo.getArea()));
                            alarmInfo.setResourceTypeName(dicItemCodeMapping.get(alarmInfo.getResourceType()));
                        }
                    });
                }
            } else if (GeograTypeEnum.GATEMACHINE.getCode().equals(typeNum)) {
                list = gateMachineMapper.getGeograObjDto();
            }
            if (!CollectionUtils.isEmpty(list)) {
                result.addAll(list);
            }
        }
        return result;
    }
}
