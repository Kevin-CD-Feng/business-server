package com.xtxk.recognition.prepare.service.svc.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xtxk.recognition.prepare.service.dto.EvenalgorithmicBinding.*;
import com.xtxk.recognition.prepare.service.dto.alarmDefine.PageAlarmDefineReqDto;
import com.xtxk.recognition.prepare.service.entity.AlarmDefine;
import com.xtxk.recognition.prepare.service.entity.AlgorithmicModel;
import com.xtxk.recognition.prepare.service.entity.DicItem;
import com.xtxk.recognition.prepare.service.entity.EvenalgorithmicBinding;
import com.xtxk.recognition.prepare.service.enums.ErrorCode;
import com.xtxk.recognition.prepare.service.exception.ServiceException;
import com.xtxk.recognition.prepare.service.mapper.AlarmDefineMapper;
import com.xtxk.recognition.prepare.service.mapper.AlgorithmicModelMapper;
import com.xtxk.recognition.prepare.service.mapper.DicItemMapper;
import com.xtxk.recognition.prepare.service.mapper.EvenalgorithmicBindingMapper;
import com.xtxk.recognition.prepare.service.svc.EvenalgorithmicBindingService;
import com.xtxk.recognition.prepare.service.utils.ObjectCovertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EvenalgorithmicBindingServiceImpl implements EvenalgorithmicBindingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EvenalgorithmicBindingServiceImpl.class);

    @Autowired
    private EvenalgorithmicBindingMapper evenalgorithmicBindingMapper;

    @Autowired
    private AlarmDefineMapper alarmDefineMapper;

    @Autowired
    private AlgorithmicModelMapper algorithmicModelMapper;

    @Autowired
    private DicItemMapper dicItemMapper;

    @Override
    @Transactional
    public void add(AddAlgorithmicBindingReqDto addAlgorithmicBindingReqDto) {
        /** 校验告警事件对应绑定算法 */
        String eventCode = addAlgorithmicBindingReqDto.getEventCode();
        String algorithmicNameCode = addAlgorithmicBindingReqDto.getAlgorithmicNameCode();

        checkEvnet2Algorithmic(eventCode,algorithmicNameCode);

        /** 校验告警事件是否存在 */
        AlarmDefine alarmDefine = alarmDefineMapper.checkEventCode(eventCode);
        if(ObjectUtil.isEmpty(alarmDefine)){
            throw new ServiceException(ErrorCode.ALARM_EVENT_NOT_ESIXT);
        }

        /** 校验绑定算法模型是否存在 */
        AlgorithmicModel algorithmicModel = algorithmicModelMapper.queryByNameCode(algorithmicNameCode);
        if(ObjectUtil.isEmpty(algorithmicModel)){
            throw new ServiceException(ErrorCode.ALGORITHMIC_MODEL_NOT_EXIST);
        }

        /** 校验事件绑定算法是否存在 */
        checkExist(eventCode,algorithmicNameCode);


        EvenalgorithmicBinding add = ObjectCovertUtils.dtoToDo(addAlgorithmicBindingReqDto, EvenalgorithmicBinding.class);
        add.setEventName(dicItemMapper.queryItemNameByItemCode(eventCode));
        add.setAlgorithmicName(dicItemMapper.queryItemNameByItemCode(algorithmicNameCode));
        add.setCreateUser("admin");
        add.setCreateTime(new Date());
        int insert = evenalgorithmicBindingMapper.insert(add);
        if(insert < 1){
            throw new ServiceException(ErrorCode.ALARM_EVENT_2_ALGORITHMIC_ADD_ERROR);
        }
    }

    /**
     * 校验事件绑定算法是否存在
     * @param eventCode
     * @param algorithmicNameCode
     */
    private void checkExist(String eventCode,String algorithmicNameCode){
        EvenalgorithmicBinding evenalgorithmicBinding = evenalgorithmicBindingMapper.checkExist(eventCode, algorithmicNameCode);
        if(ObjectUtil.isNotEmpty(evenalgorithmicBinding)){
            throw new ServiceException(ErrorCode.ALARM_EVENT_2_ALGORITHMIC_EXIST);
        }
    }

    @Override
    public PageAlgorithmicBindingRespDto pageList(PageAlgorithmicBindingReqDto pageAlgorithmicBindingReqDto) {
        PageHelper.startPage(pageAlgorithmicBindingReqDto.getPageNo(), pageAlgorithmicBindingReqDto.getPageSize());
        List<EvenalgorithmicBinding> evenalgorithmicBindings = evenalgorithmicBindingMapper.queryPageList(pageAlgorithmicBindingReqDto);
        List<AlgorithmicBindingListItemDto> algorithmicBindingListItemDtos = new ArrayList<>();
        if(evenalgorithmicBindings != null && evenalgorithmicBindings.size() > 0) {
            evenalgorithmicBindings.forEach(item -> {
                AlgorithmicBindingListItemDto algorithmicBindingListItemDto = ObjectCovertUtils.doToDto(item, AlgorithmicBindingListItemDto.class);
                algorithmicBindingListItemDtos.add(algorithmicBindingListItemDto);
            });
        }

        PageAlgorithmicBindingRespDto pageAlgorithmicBindingRespDto = new PageAlgorithmicBindingRespDto();
        PageInfo<AlgorithmicBindingListItemDto> pageInfo = new PageInfo<>(algorithmicBindingListItemDtos);
        pageAlgorithmicBindingRespDto.setCount(pageInfo.getTotal());
        pageAlgorithmicBindingRespDto.setPageCount(pageInfo.getPages());
        pageAlgorithmicBindingRespDto.setList(algorithmicBindingListItemDtos);
        return pageAlgorithmicBindingRespDto;
    }

    @Override
    public QueryAlgorithmicBindingDetailRespDto detail(Integer id) {
        EvenalgorithmicBinding detail = checkAlgorithmicBindingInfo(id);
        return ObjectCovertUtils.doToDto(detail, QueryAlgorithmicBindingDetailRespDto.class);
    }

    @Override
    @Transactional
    public void update(UpdateAlgorithmicBindingReqDto updateAlgorithmicBindingReqDto) {
        /** 校验告警事件是否存在 */
        checkAlgorithmicBindingInfo(updateAlgorithmicBindingReqDto.getId());
        String eventCode = updateAlgorithmicBindingReqDto.getEventCode();
        String algorithmicNameCode = updateAlgorithmicBindingReqDto.getAlgorithmicNameCode();
        checkEvnet2Algorithmic(eventCode,algorithmicNameCode);

        /** 校验告警事件是否存在 */
        AlarmDefine alarmDefine = alarmDefineMapper.checkEventCode(eventCode);
        if(ObjectUtil.isEmpty(alarmDefine)){
            throw new ServiceException(ErrorCode.ALARM_EVENT_NOT_ESIXT);
        }

        /** 校验绑定算法模型是否存在 */
        AlgorithmicModel algorithmicModel = algorithmicModelMapper.queryByNameCode(algorithmicNameCode);
        if(ObjectUtil.isEmpty(algorithmicModel)){
            throw new ServiceException(ErrorCode.ALGORITHMIC_MODEL_NOT_EXIST);
        }

        checkExist(eventCode,algorithmicNameCode);

        EvenalgorithmicBinding evenalgorithmicBinding = ObjectCovertUtils.dtoToDo(updateAlgorithmicBindingReqDto, EvenalgorithmicBinding.class);
        evenalgorithmicBinding.setUpdateTime(new Date());
        evenalgorithmicBinding.setUpdateUser("admin");
        evenalgorithmicBinding.setEventName(dicItemMapper.queryItemNameByItemCode(evenalgorithmicBinding.getEventCode()));
        evenalgorithmicBinding.setAlgorithmicName(dicItemMapper.queryItemNameByItemCode(evenalgorithmicBinding.getAlgorithmicNameCode()));
        Integer update = evenalgorithmicBindingMapper.update(evenalgorithmicBinding);
        if(update == 0){
            throw new ServiceException(ErrorCode.UPDATE_ALARM_EVENT_2_ALGORITHMIC_ERROR);
        }
    }

    @Override
    @Transactional
    public void delete(Integer[] ids) {
        Integer delete = evenalgorithmicBindingMapper.delete(ids,new Date(),"admin");
        if(delete == 0){
            throw new ServiceException(ErrorCode.DELETE_ALARM_EVENT_2_ALGORITHMIC_ERROR);
        }
    }

    @Override
    public List<Map<String, Object>> eventList() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<AlarmDefine> alarmDefines = alarmDefineMapper.queryPageList(new PageAlarmDefineReqDto());
        if(alarmDefines != null && alarmDefines.size() > 0) {
            alarmDefines.forEach(item -> {
                Map<String, Object> map = new HashMap<>();
                map.put("code", item.getEventCode().toString());
                map.put("desc", item.getEventName());
                list.add(map);
            });
            return list;
        }else{
            throw new ServiceException(ErrorCode.EVENT_LIST_NOT_EXIST);
        }

    }

    @Override
    public List<Map<String, Object>> queryAlgorithmic(String eventCode) {

        List<DicItem> dicItems = dicItemMapper.queryByItemParentCode(eventCode);
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
     * 校验事件策略配置是否存在
     * @param id
     * @return
     */
    private EvenalgorithmicBinding checkAlgorithmicBindingInfo(Integer id){
        EvenalgorithmicBinding evenalgorithmicBinding = evenalgorithmicBindingMapper.queryById(id);
        if(ObjectUtil.isEmpty(evenalgorithmicBinding)){
            throw new ServiceException(ErrorCode.ALARM_EVENT_2_ALGORITHMIC_NOT_EXIST);
        }
        return evenalgorithmicBinding;
    }

    /**
     * 校验绑定算法对应告警事件
     * @param eventCode
     * @param algorithmicNameCode
     */
    private void checkEvnet2Algorithmic(String eventCode,String algorithmicNameCode){
        String eventCode2 = dicItemMapper.queryItemParentCodeByItemCode(algorithmicNameCode);
        if(!eventCode2.equals(eventCode)){
            throw new ServiceException(ErrorCode.ALARM_EVENT_2_ALGORITHMIC_ERROR);
        }
    }


}