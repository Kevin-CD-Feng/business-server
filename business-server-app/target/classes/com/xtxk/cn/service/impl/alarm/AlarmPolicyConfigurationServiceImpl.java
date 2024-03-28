package com.xtxk.cn.service.impl.alarm;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xtxk.cn.dto.AlarmPolicyConfiguration.*;
import com.xtxk.cn.entity.AlarmDefine;
import com.xtxk.cn.entity.AlarmPolicyConfiguration;
import com.xtxk.cn.entity.MonitorDevicePO;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.mapper.*;
import com.xtxk.cn.service.alarm.AlarmPolicyConfigurationService;
import com.xtxk.cn.utils.object.ObjectCovertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lost
 * @description: alarmPolicyConfiguration业务实现层
 * @date: 2022-10-10 14:02:36
 */
@Service
public class AlarmPolicyConfigurationServiceImpl implements AlarmPolicyConfigurationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmPolicyConfigurationServiceImpl.class);

    @Autowired
    private AlarmPolicyConfigurationMapper alarmPolicyConfigurationMapper;

    @Autowired
    private HttpManager httpManager;

    @Autowired
    private EvenalgorithmicBindingMapper evenalgorithmicBindingMapper;

    @Autowired
    private AlarmDefineMapper alarmDefineMapper;

    @Autowired
    private MonitorDeviceMapper monitorDeviceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAndUpdate(AddAlarmPolicyConfigurationReqDto addAlarmPolicyConfigurationReqDto) {

        /** 根据resourceId查询事件设备绑定表 */
        List<AlarmPolicyConfiguration> alarmPolicyConfigurations = alarmPolicyConfigurationMapper.queryByResourceId(addAlarmPolicyConfigurationReqDto.getResourceId());
        String resourceId = addAlarmPolicyConfigurationReqDto.getResourceId();
        String name = addAlarmPolicyConfigurationReqDto.getName();

        List<AlarmPolicyConfigurationItemDto> alarmPolicyConfigurationItemDtoList = addAlarmPolicyConfigurationReqDto.getAlarmPolicyConfigurationItemDtoList();
        if (alarmPolicyConfigurationItemDtoList != null && alarmPolicyConfigurationItemDtoList.size() > 0) {
            /** 判断提交数据是否有相同事件 */
            List<String> collect1 = alarmPolicyConfigurationItemDtoList.stream().map(AlarmPolicyConfigurationItemDto::getEventCode).distinct().collect(Collectors.toList());
            if (collect1.size() != alarmPolicyConfigurationItemDtoList.size()) {
                throw new ServiceException(ErrorCode.ALARM_POLICY_CONFIGURATION_DATA_REPEAT);
            }
            /** 筛选新增设备事件配置 */
            List<AlarmPolicyConfigurationItemDto> collectNew = filterAdd(alarmPolicyConfigurationItemDtoList);
            /** 获取剔除新增设备事件配置数据集的集合 */
            List<AlarmPolicyConfigurationItemDto> collectOther = alarmPolicyConfigurationItemDtoList.stream().filter(item -> ObjectUtil.isNotEmpty(item.getId())).collect(Collectors.toList());

            /** 筛选删除字典项 */
            List<AlarmPolicyConfiguration> collectDelete = filterDelete(collectOther, alarmPolicyConfigurations);
            /** 先做删除操作 */
            batchDelete(collectDelete, resourceId, name);
            /** 更新 */
            batchUpdate(collectOther, resourceId, name);
            /** 批量新增 */
            List<AlarmPolicyConfiguration> alarmPolicyConfiguration2 = covertData(collectNew);
            insert(alarmPolicyConfiguration2, name, resourceId);
        } else {
            /** 提交数据为空 */
            if (alarmPolicyConfigurations != null && alarmPolicyConfigurations.size() > 0) {
                /** 根据resourceId删除数据库，同时关闭识别 */
                delete(resourceId);
                alarmPolicyConfigurations.forEach(item -> {
                    identifyClose(item.getEventCode(), resourceId);
                });
            }
        }
    }

    /**
     * 批量删除
     *
     * @param collectDelete
     */
    private void batchDelete(List<AlarmPolicyConfiguration> collectDelete, String resourceId, String name) {
        if (collectDelete != null && collectDelete.size() > 0) {
            alarmPolicyConfigurationMapper.batchDelete(collectDelete);

            collectDelete.forEach(item -> {
                identifyClose(item.getEventCode(), resourceId);
            });
        }
    }

    private void identifyClose(String eventCode, String resourceId) {
        /** 关闭识别 */
        String algorithmicCode = evenalgorithmicBindingMapper.queryAlgorithmicCodeByEventCode(eventCode);
        LOGGER.debug("algorithmicCode:{},关闭识别", algorithmicCode);
        JSONObject jsonReq = JSONUtil.createObj()
                .putOpt("algorithmCode", algorithmicCode)
                .putOpt("resourceId", resourceId);
        httpManager.identifyClose(jsonReq);
    }

    /**
     * 批量更新
     *
     * @param collectOther
     * @param resourceId
     */
    private void batchUpdate(List<AlarmPolicyConfigurationItemDto> collectOther, String resourceId, String name) {
        if (collectOther != null && collectOther.size() > 0) {
            List<AlarmPolicyConfiguration> alarmPolicyConfigurations = new ArrayList<>();
            collectOther.forEach(item -> {
                AlarmPolicyConfiguration alarmPolicyConfiguration = ObjectCovertUtils.doToDto(item, AlarmPolicyConfiguration.class);
                alarmPolicyConfiguration.setWebCoordinate(JSONUtil.toJsonStr(item.getWebCoordinateDtos()));
                alarmPolicyConfiguration.setUpdateTime(new Date());
                alarmPolicyConfigurations.add(alarmPolicyConfiguration);
            });
            alarmPolicyConfigurationMapper.batchUpdate(alarmPolicyConfigurations);
            alarmPolicyConfigurations.forEach(item -> {
                identifyOpen(item.getEventCode(), item.getWebCoordinate(), name, resourceId);
            });

        }
    }

    /**
     * 筛选需要删除的数据项
     *
     * @param collectOther
     * @param alarmPolicyConfigurations
     * @return
     */
    List<AlarmPolicyConfiguration> filterDelete(List<AlarmPolicyConfigurationItemDto> collectOther, List<AlarmPolicyConfiguration> alarmPolicyConfigurations) {
        List<Integer> collect1 = collectOther.stream().map(e -> e.getId()).collect(Collectors.toList());
        return alarmPolicyConfigurations.stream().filter(item -> !collect1
                .contains(item.getId()))
                .collect(Collectors.toList());
    }

    /**
     * 筛选新增设备事件配置
     *
     * @param alarmPolicyConfigurationItemDtoList
     * @return
     */
    private List<AlarmPolicyConfigurationItemDto> filterAdd(List<AlarmPolicyConfigurationItemDto> alarmPolicyConfigurationItemDtoList) {
        return alarmPolicyConfigurationItemDtoList.stream().filter(item -> ObjectUtil.isEmpty(item.getId())).collect(Collectors.toList());
    }

    @Override
    public ListAlarmPolicyConfigurationRespDto list(ListAlarmPolicyConfigurationReqDto listAlarmPolicyConfigurationReqDto) {
        List<AlarmPolicyConfiguration> alarmPolicyConfigurations = alarmPolicyConfigurationMapper.list(listAlarmPolicyConfigurationReqDto.getResourceId());
        if (alarmPolicyConfigurations != null && alarmPolicyConfigurations.size() > 0) {
            ListAlarmPolicyConfigurationRespDto listAlarmPolicyConfigurationRespDto = new ListAlarmPolicyConfigurationRespDto();
            List<AlarmPolicyConfigurationItemDto> alarmPolicyConfigurationItemDtoList = new ArrayList<>();
            alarmPolicyConfigurations.forEach(item -> {
                AlarmPolicyConfigurationItemDto alarmPolicyConfigurationItemDto = ObjectCovertUtils.doToDto(item, AlarmPolicyConfigurationItemDto.class);
                String webCoordinate = item.getWebCoordinate();
                JSONArray objects = JSONUtil.parseArray(webCoordinate);
                List<WebCoordinateDto> list = JSONUtil.toList(objects, WebCoordinateDto.class);
                alarmPolicyConfigurationItemDto.setWebCoordinateDtos(list);
                alarmPolicyConfigurationItemDtoList.add(alarmPolicyConfigurationItemDto);
            });
            listAlarmPolicyConfigurationRespDto.setAlarmPolicyConfigurationItemDtoList(alarmPolicyConfigurationItemDtoList);
            return listAlarmPolicyConfigurationRespDto;
        } else {
            return new ListAlarmPolicyConfigurationRespDto();
        }
    }

    @Override
    public ConfigurationListDto configurationList() {

        List<AlarmPolicyConfiguration> alarmPolicyConfigurations = alarmPolicyConfigurationMapper.queryAll();
        if (alarmPolicyConfigurations != null && alarmPolicyConfigurations.size() > 0) {
            List<ConfigurationItemDto> configurationItemDtos = new ArrayList<>();
            alarmPolicyConfigurations.forEach(item -> {
                String algorithmicCode = evenalgorithmicBindingMapper.queryAlgorithmicCodeByEventCode(item.getEventCode());
                AlarmDefine define = alarmDefineMapper.checkEventCode(item.getEventCode());
                MonitorDevicePO byId = monitorDeviceMapper.getById(item.getResourceId());
                ConfigurationItemDto configurationItemDto = new ConfigurationItemDto();
                configurationItemDto.setAlgorithmCode(algorithmicCode);
                /**
                 * 数据库存的单位是   帧/秒
                 * 算法服务单位是   毫秒/帧
                 */
                configurationItemDto.setPeroid(1000 / define.getIntervals());
                String webCoordinate = item.getWebCoordinate();
                JSONArray objects = JSONUtil.parseArray(webCoordinate);
                List<WebCoordinateDto> list = JSONUtil.toList(objects, WebCoordinateDto.class);
                JSONObject jsonObject = JSONUtil.createObj();
                Integer width = null;
                Integer height = null;

                List<Integer[]> roiList = new ArrayList<>();
                List<List<BigDecimal>> points = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    List<BigDecimal> point = new ArrayList<>();
                    width = list.get(i).getWidth();
                    height = list.get(i).getHeight();
                    Integer widthLength = list.get(i).getWidth();
                    Integer heigthLength = list.get(i).getHeight();
                    if (widthLength == 0 && heigthLength == 0) {
                        continue;
                    }
                    Integer x1 = list.get(i).getRealX();
                    Integer y1 = list.get(i).getRealY();
                    Integer x2 = x1 + widthLength;
                    Integer y2 = list.get(i).getRealY();
                    Integer x3 = x1 + widthLength;
                    Integer y3 = y1 + heigthLength;
                    Integer x4 = list.get(i).getRealX();
                    Integer y4 = y1 + heigthLength;
                    Integer[] tt = {x1, y1, x2, y2, x3, y3, x4, y4};
                    roiList.add(tt);
                    List<List<BigDecimal>> pointData = list.get(i).getPoint();
                    if (pointData != null && pointData.size() > 0) {
                        for (List<BigDecimal> d : pointData) {
                            for (BigDecimal v : d) {
                                point.add(v);
                            }
                        }
                    }
                    points.add(point);
                }
                Integer[][] integers = roiList.toArray(new Integer[][]{});
                if (points!=null && points.size() > 0) {
                    //jsonObject.putOpt("width", width).putOpt("height", height).putOpt("roi", integers);
                    jsonObject.putOpt("width", width).putOpt("height", height).putOpt("roi", points);
                }
                configurationItemDto.setResourceId(item.getResourceId());
                configurationItemDto.setResourceName(byId.getDeviceName());
                configurationItemDto.setEventCode(item.getEventCode());
                configurationItemDto.setRoi(jsonObject.size() >0 ? jsonObject.toString():"");
                configurationItemDtos.add(configurationItemDto);
            });

            ConfigurationListDto configurationListDto = new ConfigurationListDto();
            configurationListDto.setConfigurationItemDtos(configurationItemDtos);
            return configurationListDto;
        } else {
            return null;
        }
    }

    /**
     * 数据转换
     *
     * @param alarmPolicyConfigurationItemDtoList
     * @return
     */
    private List<AlarmPolicyConfiguration> covertData(List<AlarmPolicyConfigurationItemDto> alarmPolicyConfigurationItemDtoList) {
        List<AlarmPolicyConfiguration> alarmPolicyConfigurations = new ArrayList<>();
        Date now = new Date();
        alarmPolicyConfigurationItemDtoList.forEach(item -> {
            AlarmPolicyConfiguration alarmPolicyConfiguration = ObjectCovertUtils.dtoToDo(item, AlarmPolicyConfiguration.class);

            /** 校验告警事件是在存在 */
            List<WebCoordinateDto> webData = item.getWebCoordinateDtos();
            alarmPolicyConfiguration.setWebCoordinate(JSONUtil.toJsonStr(webData));
            alarmPolicyConfiguration.setCreateTime(now);
            alarmPolicyConfiguration.setCreateUser("admin");
            alarmPolicyConfigurations.add(alarmPolicyConfiguration);
        });
        return alarmPolicyConfigurations;
    }

    /**
     * 新增数据
     *
     * @param alarmPolicyConfigurations
     * @param name
     */
    private void insert(List<AlarmPolicyConfiguration> alarmPolicyConfigurations, String name, String resourceId) {
        /** 新增事件策略配置信息 */
        if (alarmPolicyConfigurations != null && alarmPolicyConfigurations.size() > 0) {
            alarmPolicyConfigurationMapper.insert(alarmPolicyConfigurations);
            alarmPolicyConfigurations.forEach(item -> {

                identifyOpen(item.getEventCode(), item.getWebCoordinate(), name, resourceId);

            });
        }
    }

    private void identifyOpen(String eventCode, String webCoordinate, String name, String resourceId) {
        /** 开启识别 */
        String algorithmicCode = evenalgorithmicBindingMapper.queryAlgorithmicCodeByEventCode(eventCode);
        AlarmDefine define = alarmDefineMapper.checkEventCode(eventCode);
        LOGGER.debug("algorithmicCode:{},开启识别", algorithmicCode);

        JSONArray objects = JSONUtil.parseArray(webCoordinate);
        List<WebCoordinateDto> list = JSONUtil.toList(objects, WebCoordinateDto.class);
        Integer width = null;
        Integer height = null;
        List<Integer[]> roiList = new ArrayList<>();
        List<List<BigDecimal>> pointResult = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            List<BigDecimal> point = new ArrayList();
            width = list.get(i).getWidth();
            height = list.get(i).getHeight();
            Integer widthLength = list.get(i).getWidth();
            Integer heigthLength = list.get(i).getHeight();
            if (widthLength == 0 && heigthLength == 0) {
                continue;
            }
            Integer x1 = list.get(i).getRealX();
            Integer y1 = list.get(i).getRealY();
            Integer x2 = x1 + widthLength;
            Integer y2 = list.get(i).getRealY();
            Integer x3 = x1 + widthLength;
            Integer y3 = y1 + heigthLength;
            Integer x4 = list.get(i).getRealX();
            Integer y4 = y1 + heigthLength;
            Integer[] tt = {x1, y1, x2, y2, x3, y3, x4, y4};
            roiList.add(tt);
            List<List<BigDecimal>> pointData = list.get(i).getPoint();
            if (pointData != null && pointData.size() > 0) {
                for (List<BigDecimal> d : pointData) {
                    for (BigDecimal v : d) {
                        point.add(v);
                    }
                }
            }
            pointResult.add(point);
        }
        Integer[][] integers = roiList.toArray(new Integer[][]{});
        JSONObject jsonObject = JSONUtil.createObj();
        if (pointResult.size() > 0) {
            //jsonObject.putOpt("width", width).putOpt("height", height).putOpt("roi", integers);
            jsonObject.putOpt("width", width).putOpt("height", height).putOpt("roi", pointResult);
        }
        JSONObject jsonReq = JSONUtil.createObj()
                .putOpt("algorithmCode", algorithmicCode)
                .putOpt("period", 1000 / define.getIntervals())
                .putOpt("eventCode", define.getEventCode())
                .putOpt("resourceId", resourceId)
                .putOpt("resourceName", name);
        if (jsonObject.size() > 0) {
            jsonReq.putOpt("roi", jsonObject.toString());
        }
        httpManager.identifyOpen(jsonReq);
    }

    /**
     * 根据resourceId删除事件策略配置信息和设备信息表
     *
     * @param resourceId
     */
    private void delete(String resourceId) {
        /** 删除事件策略配置信息 */
        alarmPolicyConfigurationMapper.deleteByResourceId(resourceId);
    }

    /**
     * 校验resourceId是否一致
     *
     * @param alarmPolicyConfigurationItemDtoList
     */
    private String checkResourceId(List<AlarmPolicyConfigurationItemDto> alarmPolicyConfigurationItemDtoList) {
        String resourceId = null;
        for (AlarmPolicyConfigurationItemDto item : alarmPolicyConfigurationItemDtoList) {
            String resourceId1 = item.getResourceId();
            if (ObjectUtil.isEmpty(resourceId)) {
                resourceId = resourceId1;
            } else {
                if (!resourceId.equals(resourceId1)) {
                    throw new ServiceException(ErrorCode.ALARM_POLICY_CONFIGURATION_RESOURCEID_ERROR);
                }
            }
        }
        return resourceId;
    }
}