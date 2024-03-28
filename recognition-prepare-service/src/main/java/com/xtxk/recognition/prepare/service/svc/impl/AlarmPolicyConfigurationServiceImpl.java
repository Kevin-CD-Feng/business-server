package com.xtxk.recognition.prepare.service.svc.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xtxk.recognition.prepare.service.algAction.AlgBucket;
import com.xtxk.recognition.prepare.service.algAction.AlgDispatcher;
import com.xtxk.recognition.prepare.service.algAction.AlgLoopModel;
import com.xtxk.recognition.prepare.service.algAction.AlgModel;
import com.xtxk.recognition.prepare.service.dto.AlarmPolicyConfiguration.*;
import com.xtxk.recognition.prepare.service.dto.loopAlg.AlgBucketDisplayDto;
import com.xtxk.recognition.prepare.service.dto.loopAlg.LoopAlgResVo;
import com.xtxk.recognition.prepare.service.entity.AlarmDefine;
import com.xtxk.recognition.prepare.service.entity.AlarmPolicyConfiguration;
import com.xtxk.recognition.prepare.service.entity.DicItem;
import com.xtxk.recognition.prepare.service.enums.ErrorCode;
import com.xtxk.recognition.prepare.service.exception.ServiceException;
import com.xtxk.recognition.prepare.service.manager.HttpManager;
import com.xtxk.recognition.prepare.service.mapper.*;
import com.xtxk.recognition.prepare.service.svc.AlarmPolicyConfigurationService;
import com.xtxk.recognition.prepare.service.svc.AlgDispatcherService;
import com.xtxk.recognition.prepare.service.utils.ObjectCovertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


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
    private LoopAlgResMapper loopAlgResMapper;

    @Autowired
    private AlgDispatcherService algDispatcherService;

    @Autowired
    private AlgDispatcher dispatcher;

    @Autowired
    private DicItemMapper dicItemMapper;

    @Autowired
    private AlgDispatcher algDispatcher;

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
            List<AlarmPolicyConfigurationItemDto> collectOther = alarmPolicyConfigurationItemDtoList.stream()
                    .filter(item -> ObjectUtil.isNotEmpty(item.getId()) && item.getId() != 0)
                    .collect(Collectors.toList());

            /** 筛选删除字典项 */
            List<AlarmPolicyConfiguration> collectDelete = filterDelete(collectOther, alarmPolicyConfigurations);
            /** 先做删除操作 */
            batchDelete(collectDelete, resourceId, name);

            /**只是处理新增和更新，删除部分在轮询页面处理*/
            /** 更新 */
            batchUpdate(collectOther, resourceId, name);

            /** 批量新增 */
            List<AlarmPolicyConfiguration> alarmPolicyConfiguration2 = covertData(collectNew);
            insert(alarmPolicyConfiguration2, name, resourceId);

            HashMap<String, List<AlgLoopModel>> lines = this.algDispatcherService.queryAlgLoopModelByCondition(resourceId, null);
            lines.forEach((k, v) -> {
                if (this.dispatcher.getBuckets().containsKey(k)) {
                    AlgBucket bucket = this.dispatcher.getBuckets().get(k);
                    v.forEach(bucket::updateAlgLoopModelByResId);
                }
            });

        } else {
            /** 提交数据为空 */
            if (alarmPolicyConfigurations != null && alarmPolicyConfigurations.size() > 0) {
                /** 根据resourceId删除数据库，同时关闭识别 */
                delete(resourceId);
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
        }
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
                alarmPolicyConfiguration.setAlgCode(item.getAlgCode());
                alarmPolicyConfiguration.setViolationInterval(item.getViolationInterval());
                alarmPolicyConfiguration.setViolationDuration(item.getViolationDuration());
                alarmPolicyConfiguration.setViolationBeginDate(item.getViolationBeginDate());
                alarmPolicyConfiguration.setViolationEndDate(item.getViolationEndDate());
                alarmPolicyConfiguration.setFrameInterval(item.getFrameInterval());
                alarmPolicyConfigurations.add(alarmPolicyConfiguration);
            });
            alarmPolicyConfigurationMapper.batchUpdate(alarmPolicyConfigurations);

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
        return alarmPolicyConfigurationItemDtoList.stream()
                .filter(item -> ObjectUtil.isEmpty(item.getId()) || item.getId() == 0)
                .collect(Collectors.toList());
    }

    @Override
    public ListAlarmPolicyConfigurationRespDto list(ListAlarmPolicyConfigurationReqDto listAlarmPolicyConfigurationReqDto) {
        List<AlarmPolicyConfiguration> alarmPolicyConfigurations = alarmPolicyConfigurationMapper.list(listAlarmPolicyConfigurationReqDto.getResourceId());
        ListAlarmPolicyConfigurationRespDto listAlarmPolicyConfigurationRespDto = new ListAlarmPolicyConfigurationRespDto();
        List<AlarmPolicyConfigurationItemDto> alarmPolicyConfigurationItemDtoList = new ArrayList<>();
        HashMap<String, String> exists = new HashMap<>();

        List<DicItem> alarm_event = this.dicItemMapper.queryDicItemListByDicCode("alarm_event");
        List<DicItem> alarm_type = this.dicItemMapper.queryDicItemListByDicCode("algorithm_name");
        Map<String, String> eventMap = alarm_event.stream().collect(Collectors.toMap(DicItem::getItemCode, DicItem::getItemName));
        Map<String, String> alarmTypeMap = alarm_type.stream().collect(Collectors.toMap(DicItem::getItemCode, DicItem::getItemName));
        alarmPolicyConfigurations.forEach(item -> {
            AlarmPolicyConfigurationItemDto alarmPolicyConfigurationItemDto = ObjectCovertUtils.doToDto(item, AlarmPolicyConfigurationItemDto.class);
            String webCoordinate = item.getWebCoordinate();
            JSONArray objects = JSONUtil.parseArray(webCoordinate);
            List<WebCoordinateDto> list = JSONUtil.toList(objects, WebCoordinateDto.class);
            alarmPolicyConfigurationItemDto.setWebCoordinateDtos(list);
            alarmPolicyConfigurationItemDto.setAlgCodeDesc(alarmTypeMap.get(alarmPolicyConfigurationItemDto.getAlgCode()));
            alarmPolicyConfigurationItemDto.setEventCodeDesc(eventMap.get(alarmPolicyConfigurationItemDto.getEventCode()));
            alarmPolicyConfigurationItemDto.setFrameInterval(item.getFrameInterval());
            alarmPolicyConfigurationItemDtoList.add(alarmPolicyConfigurationItemDto);
            String[] splits = {item.getAlgCode().trim(), item.getEventCode().trim(), item.getResourceId().trim()};
            String contextKey = String.join("_", splits);
            exists.put(contextKey, contextKey);
        });
        //从轮询组中获取数据补充，切记返回上去的id为空
        List<LoopAlgResVo> loopAlgResVos = this.loopAlgResMapper.queryloopResByResId(listAlarmPolicyConfigurationReqDto.getResourceId());
        loopAlgResVos.forEach(it -> {
            String[] splits = {it.getAlgCode().trim(), it.getEventCode().trim(), it.getResourceId().trim()};
            String contextKey = String.join("_", splits);
            if (!exists.containsKey(contextKey)) {
                AlarmPolicyConfigurationItemDto dto = new AlarmPolicyConfigurationItemDto();
                dto.setWebCoordinateDtos(new ArrayList<>());
                dto.setEventCode(it.getEventCode());
                dto.setAlgCode(it.getAlgCode());
                dto.setFrameInterval(0F);
//                dto.setFrameInterval(0);

                dto.setAlgCodeDesc(alarmTypeMap.get(it.getAlgCode()));
                dto.setEventCodeDesc(eventMap.get(it.getEventCode()));
                dto.setViolationLength(null);
                dto.setId(null);
                dto.setResourceId(listAlarmPolicyConfigurationReqDto.getResourceId());
                alarmPolicyConfigurationItemDtoList.add(dto);
                exists.put(contextKey, contextKey);
            }
        });

        listAlarmPolicyConfigurationRespDto.setAlarmPolicyConfigurationItemDtoList(alarmPolicyConfigurationItemDtoList);
        return listAlarmPolicyConfigurationRespDto;
    }

    @Override
    public ConfigurationListDto configurationList() {
        List<ConfigurationItemDto> configurationItemDtos = new ArrayList<>();
        this.algDispatcher.getBuckets().forEach((k, vi) -> {
            vi.getActions().forEach((ik, iv) -> {
                AlgModel algModel = iv.getAlgModel();
                ConfigurationItemDto configurationItemDto = new ConfigurationItemDto();
                configurationItemDto.setAlgorithmCode(algModel.getAlgCode());
                Float frameInterval = algModel.getFrameInterval();
                if (frameInterval == null) {
                    frameInterval = 1F;
                }
                configurationItemDto.setPeroid((int) (frameInterval * 1000));
                configurationItemDto.setResourceId(algModel.getResourceId());
                configurationItemDto.setResourceName(algModel.getResourceName());
                configurationItemDto.setEventCode(algModel.getEventCode());
                configurationItemDto.setInterval(algModel.getInterval());
                if (algModel.getInterval() == 0) {
                    configurationItemDto.setInterval(null);
                }
                configurationItemDto.setDuration(algModel.getDuration());

                String webCoordinate = algModel.getWebCoordinate();
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
                    Integer x1 = Optional.ofNullable(list.get(i).getRealX()).orElse(0);
                    Integer y1 = Optional.ofNullable(list.get(i).getRealY()).orElse(0);
                    Integer x2 = x1 + widthLength;
                    Integer y2 = Optional.ofNullable(list.get(i).getRealY()).orElse(0);
                    Integer x3 = x1 + widthLength;
                    Integer y3 = y1 + heigthLength;
                    Integer x4 = Optional.ofNullable(list.get(i).getRealX()).orElse(0);
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
                //Integer[][] integers = roiList.toArray(new Integer[][]{});
                if (points.size() > 0) {
                    jsonObject.putOpt("width", width).putOpt("height", height).putOpt("roi", points);
                }
                configurationItemDto.setRoi(jsonObject.size() > 0 ? jsonObject.toString() : "");
                configurationItemDtos.add(configurationItemDto);
            });
        });

        ConfigurationListDto configurationListDto = new ConfigurationListDto();
        configurationListDto.setConfigurationItemDtos(configurationItemDtos);
        return configurationListDto;
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
            alarmPolicyConfiguration.setFrameInterval(item.getFrameInterval());
            alarmPolicyConfiguration.setCreateTime(now);
            alarmPolicyConfiguration.setCreateUser("admin");
            alarmPolicyConfiguration.setAlgCode(item.getAlgCode());
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
        }
    }

//    private void identifyOpen(String eventCode, String webCoordinate, String name, String resourceId) {
//        /** 开启识别 */
//        String algorithmicCode = evenalgorithmicBindingMapper.queryAlgorithmicCodeByEventCode(eventCode);
//        AlarmDefine define = alarmDefineMapper.checkEventCode(eventCode);
//        LOGGER.debug("algorithmicCode:{},开启识别", algorithmicCode);
//
//        JSONArray objects = JSONUtil.parseArray(webCoordinate);
//        List<WebCoordinateDto> list = JSONUtil.toList(objects, WebCoordinateDto.class);
//        Integer width = null;
//        Integer height = null;
//        List<Integer[]> roiList = new ArrayList<>();
//        List<List<BigDecimal>> pointResult = new ArrayList<>();
//
//        for (int i = 0; i < list.size(); i++) {
//            List<BigDecimal> point = new ArrayList();
//            width = list.get(i).getWidth();
//            height = list.get(i).getHeight();
//            Integer widthLength = list.get(i).getWidth();
//            Integer heigthLength = list.get(i).getHeight();
//            if (widthLength == 0 && heigthLength == 0) {
//                continue;
//            }
//            Integer x1 = list.get(i).getRealX();
//            Integer y1 = list.get(i).getRealY();
//            Integer x2 = x1 + widthLength;
//            Integer y2 = list.get(i).getRealY();
//            Integer x3 = x1 + widthLength;
//            Integer y3 = y1 + heigthLength;
//            Integer x4 = list.get(i).getRealX();
//            Integer y4 = y1 + heigthLength;
//            Integer[] tt = {x1, y1, x2, y2, x3, y3, x4, y4};
//            roiList.add(tt);
//            List<List<BigDecimal>> pointData = list.get(i).getPoint();
//            if (pointData != null && pointData.size() > 0) {
//                for (List<BigDecimal> d : pointData) {
//                    for (BigDecimal v : d) {
//                        point.add(v);
//                    }
//                }
//            }
//            pointResult.add(point);
//        }
//        Integer[][] integers = roiList.toArray(new Integer[][]{});
//        JSONObject jsonObject = JSONUtil.createObj();
//        if (pointResult.size() > 0) {
//            //jsonObject.putOpt("width", width).putOpt("height", height).putOpt("roi", integers);
//            jsonObject.putOpt("width", width).putOpt("height", height).putOpt("roi", pointResult);
//        }
//        JSONObject jsonReq = JSONUtil.createObj()
//                .putOpt("algorithmCode", algorithmicCode)
//                .putOpt("period", 1000 / define.getIntervals())
//                .putOpt("eventCode", define.getEventCode())
//                .putOpt("resourceId", resourceId)
//                .putOpt("resourceName", name);
//        if (jsonObject.size() > 0) {
//            jsonReq.putOpt("roi", jsonObject.toString());
//        }
//        httpManager.identifyOpen(jsonReq);
//    }

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


    /**
     * 查询已配置策略设备列表
     *
     * @return
     */
    @Override
    public List<ListAlarmPolicyDeviceDto> getAlarmPolicyDeviceList(String deviceName) {
        List<ListAlarmPolicyDeviceDto> alarmPolicyDeviceDtoList = new ArrayList<>();
        List<AlarmPolicyDeviceDto> list = alarmPolicyConfigurationMapper.getAlarmPolicyDeviceList(deviceName);
        Map<String, List<AlarmPolicyDeviceDto>> collect = list.stream().collect(Collectors.groupingBy(AlarmPolicyDeviceDto::getEventName));

        for (Map.Entry<String, List<AlarmPolicyDeviceDto>> entry : collect.entrySet()) {
            ListAlarmPolicyDeviceDto alarmPolicyDeviceDto = new ListAlarmPolicyDeviceDto();
            List<AlarmPolicyDeviceDto> data = entry.getValue();
            alarmPolicyDeviceDto.setEventName(entry.getKey());
            alarmPolicyDeviceDto.setDeviceList(data);
            alarmPolicyDeviceDtoList.add(alarmPolicyDeviceDto);
        }

        return alarmPolicyDeviceDtoList;
    }
}