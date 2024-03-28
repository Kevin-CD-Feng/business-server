package com.xtxk.cn.service.impl.alarm;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xtxk.cn.dto.alarmDefine.*;
import com.xtxk.cn.entity.AlarmDefine;
import com.xtxk.cn.entity.DicItem;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.mapper.AlarmDefineMapper;
import com.xtxk.cn.mapper.AlarmPolicyConfigurationMapper;
import com.xtxk.cn.mapper.DicItemMapper;
import com.xtxk.cn.mapper.DicMapper;
import com.xtxk.cn.service.alarm.AlarmDefineService;

import com.xtxk.cn.utils.object.ObjectCovertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: lost
 * @description: alarmDefine业务实现层
 * @date: 2022-10-10 14:02:36
 */
@Service
public class AlarmDefineServiceImpl implements AlarmDefineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmDefineServiceImpl.class);

    @Autowired
    private AlarmDefineMapper alarmDefineMapper;

    @Autowired
    private DicItemMapper dicItemMapper;

    @Autowired
    private DicMapper dicMapper;

    @Autowired
    private AlarmPolicyConfigurationMapper alarmPolicyConfigurationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(AddAlarmDefineReqDto addAlarmDefineReqDto) {
        /** 校验告警事件是否存在 */
        checkEvent(addAlarmDefineReqDto.getEventCode());

        /** 校验告警事件对应的事件类型是否正确 */
        checkRelation(addAlarmDefineReqDto.getEventCode(),addAlarmDefineReqDto.getTypeCode());

        AlarmDefine alarmDefine = ObjectCovertUtils.dtoToDo(addAlarmDefineReqDto, AlarmDefine.class);
        if(ObjectUtil.isEmpty(alarmDefine)){
            throw new ServiceException(ErrorCode.ALARM_DATA_COVERT_ERROR);
        }
        /** 根据事件编码查询事件名称 */
        String eventName = dicItemMapper.queryItemNameByItemCode(alarmDefine.getEventCode());
        if(ObjectUtil.isEmpty(eventName)){
            throw new ServiceException(ErrorCode.ALARM_EVENT_NAME_NOT_EXIST);
        }
        alarmDefine.setEventName(eventName);
        alarmDefine.setCreateTime(new Date());
        alarmDefine.setCreateUser("admin");
        alarmDefineMapper.insert(alarmDefine);
    }

    @Override
    public DetailAlarmDefineRespDto detail(Integer id) {
        AlarmDefine detail = alarmDefineMapper.queryById(id);
        if(ObjectUtil.isEmpty(detail)){
            throw new ServiceException(ErrorCode.ALARM_EVENT_NOT_ESIXT);
        }
        return ObjectCovertUtils.doToDto(detail, DetailAlarmDefineRespDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateAlarmDefineReqDto updateAlarmDefineReqDto) {
        /** 校验告警事件是否存在 */
        checkAlarmInfo(updateAlarmDefineReqDto.getId());

        checkRelation(updateAlarmDefineReqDto.getEventCode(),updateAlarmDefineReqDto.getTypeCode());

        AlarmDefine define = ObjectCovertUtils.dtoToDo(updateAlarmDefineReqDto, AlarmDefine.class);
        define.setUpdateTime(new Date());
        define.setUpdateUser("admin");
        /** 根据事件编码查询事件名称 */
        String eventName = dicItemMapper.queryItemNameByItemCode(define.getEventCode());
        if(ObjectUtil.isEmpty(eventName)){
            throw new ServiceException(ErrorCode.ALARM_EVENT_NAME_NOT_EXIST);
        }
        define.setEventName(eventName);
        Integer update = alarmDefineMapper.update(define);
        if(update == 0){
            throw new ServiceException(ErrorCode.UPDATE_ALARM_EVENT_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer[] ids) {

        for(Integer id : ids){
            /** 校验告警事件是否存在 */
            checkAlarmInfo(id);

            /** 是否绑定设备 */
            checkCorrelationDevice(id);

            /** 是否绑定算法 */
            checkCorrelationAlgorithmic(id);
        }
        Integer delete = alarmDefineMapper.delete(ids,new Date(),"admin");
        if(delete == 0){
            throw new ServiceException(ErrorCode.DELETE_EVENT_ERROR);
        }
    }

    @Override
    public PageAlarmDefineRespDto pageList(PageAlarmDefineReqDto pageAlarmDefineReqDto) {
        PageHelper.startPage(pageAlarmDefineReqDto.getPageNo(), pageAlarmDefineReqDto.getPageSize());
        List<AlarmDefine> alarmDefines = alarmDefineMapper.queryPageList(pageAlarmDefineReqDto);
        List<AlarmDefineListItemDto> alarmDefineItemDtos = new ArrayList<>();
        if(alarmDefines != null && alarmDefines.size() > 0) {
            alarmDefines.forEach(item -> {
                AlarmDefineListItemDto alarmDefineListItemDto = ObjectCovertUtils.doToDto(item, AlarmDefineListItemDto.class);
                String typeName = dicItemMapper.queryItemNameByItemCode(item.getTypeCode());
                alarmDefineListItemDto.setTypeName(typeName);
                alarmDefineItemDtos.add(alarmDefineListItemDto);
            });
        }

        PageAlarmDefineRespDto pageAlarmDefineRespDto = new PageAlarmDefineRespDto();
        PageInfo<AlarmDefineListItemDto> pageInfo = new PageInfo<>(alarmDefineItemDtos);
        pageAlarmDefineRespDto.setCount(pageInfo.getTotal());
        pageAlarmDefineRespDto.setPageCount(pageInfo.getPages());
        pageAlarmDefineRespDto.setList(alarmDefineItemDtos);
        return pageAlarmDefineRespDto;
    }

    /**
     * 是否绑定算法
     * @param id
     */
    private void checkCorrelationAlgorithmic(Integer id){
        AlarmDefine define = alarmDefineMapper.checkCorrelationAlgorithmic(id);
        if(ObjectUtil.isNotEmpty(define)){
            throw new ServiceException(ErrorCode.EVENT_BINDING_ALGORITHMIC_ERROR);
        }
    }

    /**
     * 是否绑定设备
     * @param id
     */
    private void checkCorrelationDevice(Integer id){
        AlarmDefine define = alarmDefineMapper.checkCorrelationDevice(id);
        if(ObjectUtil.isNotEmpty(define)){
            throw new ServiceException(ErrorCode.EVENT_BINDING_DEVICE_ERROR);
        }
    }

    /**
     * 校验告警事件是否存在
     * @param eventCode
     * @return
     */
    public void checkEvent(String eventCode){
        AlarmDefine alarmDefine = alarmDefineMapper.checkEventCode(eventCode);
        if(ObjectUtil.isNotEmpty(alarmDefine)){
            throw new ServiceException(ErrorCode.ALARM_EVENT_ESIXT);
        }
    }

    @Override
    public List<Map<String, Object>> queryEventTypeList() {

        /** 根据事件类型编码查询事件类型列表 */
        List<DicItem> dicItems = dicItemMapper.queryDicItemList("alarm_type");
        if(dicItems != null && dicItems.size() > 0){
            List<Map<String, Object>> eventTypes = new ArrayList<>();
            dicItems.forEach(item -> {
                Map<String, Object> map = new HashMap<>();
                map.put("desc",item.getItemName());
                map.put("code",item.getItemCode());
                eventTypes.add(map);
            });
            return eventTypes;
        }else{
            return new ArrayList<>();
        }
    }

    @Override
    public List<Map<String, Object>> type2Event(String eventType) {
        List<DicItem> dicItems = dicItemMapper.queryByItemParentCode(eventType);
        if(dicItems != null && dicItems.size() > 0){
            List<Map<String, Object>> eventTypes = new ArrayList<>();
            dicItems.forEach(item -> {
                Map<String, Object> map = new HashMap<>();
                map.put("desc",item.getItemName());
                map.put("code",item.getItemCode());
                eventTypes.add(map);
            });
            return eventTypes;
        }else{
            return new ArrayList<>();
        }
    }


    /**
     * 校验告警事件对应的事件类型是否正确
     * @param eventCode
     * @param typeCode
     */
    private void checkRelation(String eventCode,String typeCode){
        String typeByCode = dicItemMapper.queryItemParentCodeByItemCode(eventCode);
        if(!typeByCode.equals(typeCode)){
            throw new ServiceException(ErrorCode.ALARM_EVENT_TYPE_NOT_MATCH);
        }
    }

    /**
     * 根据id校验告警事件
     * @param id
     */
    private void checkAlarmInfo(Integer id){
        AlarmDefine detail = alarmDefineMapper.queryById(id);
        if(ObjectUtil.isEmpty(detail)){
            throw new ServiceException(ErrorCode.ALARM_EVENT_NOT_ESIXT);
        }
    }
}