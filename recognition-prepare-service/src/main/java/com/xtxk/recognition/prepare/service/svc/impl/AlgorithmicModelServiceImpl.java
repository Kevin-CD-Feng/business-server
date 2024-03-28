package com.xtxk.recognition.prepare.service.svc.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xtxk.recognition.prepare.service.dto.algorithmicModel.*;
import com.xtxk.recognition.prepare.service.entity.AlgorithmicModel;
import com.xtxk.recognition.prepare.service.entity.DicItem;
import com.xtxk.recognition.prepare.service.enums.ErrorCode;
import com.xtxk.recognition.prepare.service.exception.ServiceException;
import com.xtxk.recognition.prepare.service.manager.HttpManager;
import com.xtxk.recognition.prepare.service.mapper.AlgorithmicModelMapper;
import com.xtxk.recognition.prepare.service.mapper.DicItemMapper;
import com.xtxk.recognition.prepare.service.svc.AlgorithmicModelService;
import com.xtxk.recognition.prepare.service.utils.ObjectCovertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: lost
 * @description: algorithmicModel业务实现层
 * @date: 2022-10-10 14:02:36
 */
@Service
public class AlgorithmicModelServiceImpl implements AlgorithmicModelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlgorithmicModelServiceImpl.class);

    @Autowired
    private AlgorithmicModelMapper algorithmicModelMapper;

    @Autowired
    private DicItemMapper dicItemMapper;

    @Autowired
    private HttpManager httpManager;

    @Override
    @Transactional
    public void add(AddAlgorithmicModelReqDto addAlgorithmicModelReqDto) {
        /** 校验算法code是否存在 */
        String nameCode = addAlgorithmicModelReqDto.getNameCode();
        String name = checkCode(nameCode);
        checkCodeExist(nameCode);
        AlgorithmicModel algorithmicModel = ObjectCovertUtils.dtoToDo(addAlgorithmicModelReqDto, AlgorithmicModel.class);
        algorithmicModel.setCreateTime(new Date());
        algorithmicModel.setCreateUser("admin");
        algorithmicModel.setName(name);
        Integer insert = algorithmicModelMapper.insert(algorithmicModel);
        if(insert > 0){
            /** 开启算法识别 */
            LOGGER.debug("nameCode:{},开启算法",nameCode);
            List<DicItem> algorithm_name = dicItemMapper.queryDicItemListByDicCode("algorithm_name");
            Map<String, DicItem> algorithmMap = algorithm_name.stream().collect(Collectors.toMap(DicItem::getItemCode, it -> it));
            JSONObject jsonReq = JSONUtil.createObj()
                    .putOpt("algorithmCode", nameCode)
                    .putOpt("algorithmReqJsonStr", addAlgorithmicModelReqDto.getParameterSample())
                    .putOpt("algorithmUrl", addAlgorithmicModelReqDto.getUrl())
                    .putOpt("algorithmName",algorithmMap.get(nameCode).getItemName());
            httpManager.algorithmOpen(jsonReq);
        }
    }

    @Override
    public DetailAlgorithmicModelRespDto detail(Integer id) {
        AlgorithmicModel algorithmicModel = algorithmicModelMapper.queryById(id);
        if(ObjectUtil.isEmpty(algorithmicModel)){
            throw new ServiceException(ErrorCode.ALGORITHMIC_MODEL_NOT_EXIST);
        }
        return ObjectCovertUtils.doToDto(algorithmicModel, DetailAlgorithmicModelRespDto.class);
    }

    @Override
    @Transactional
    public void update(UpdateAlgorithmicModelReqDto updateAlgorithmicModelReqDto) {
        String name = checkCode(updateAlgorithmicModelReqDto.getNameCode());
        AlgorithmicModel algorithmicModel = algorithmicModelMapper.queryById(updateAlgorithmicModelReqDto.getId());
        if(ObjectUtil.isEmpty(algorithmicModel)){
            throw new ServiceException(ErrorCode.ALGORITHMIC_MODEL_NOT_EXIST);
        }
        AlgorithmicModel algorithmicModel1 = ObjectCovertUtils.dtoToDo(updateAlgorithmicModelReqDto, AlgorithmicModel.class);
        algorithmicModel1.setUpdateTime(new Date());
        algorithmicModel1.setUpdateUser("admin");
        algorithmicModel1.setName(name);
        Integer update = algorithmicModelMapper.update(algorithmicModel1);
        if(update == 0){
            throw new ServiceException(ErrorCode.UPDATE_ALGORITHMIC_MODEL_ERROR);
        }else{
            /** 开启算法识别 */
            LOGGER.debug("nameCode:{},开启算法",updateAlgorithmicModelReqDto.getNameCode());
            List<DicItem> algorithm_name = dicItemMapper.queryDicItemListByDicCode("algorithm_name");
            Map<String, DicItem> algorithmMap = algorithm_name.stream().collect(Collectors.toMap(DicItem::getItemCode, it -> it));
            JSONObject jsonReq = JSONUtil.createObj()
                    .putOpt("algorithmCode", updateAlgorithmicModelReqDto.getNameCode())
                    .putOpt("algorithmReqJsonStr", updateAlgorithmicModelReqDto.getParameterSample())
                    .putOpt("algorithmUrl", updateAlgorithmicModelReqDto.getUrl())
                    .putOpt("algorithmName",algorithmMap.get(updateAlgorithmicModelReqDto.getNameCode())
                            .getItemName());
            httpManager.algorithmOpen(jsonReq);
        }
    }

    @Override
    @Transactional
    public void delete(Integer[] ids) {
        List<AlgorithmicModel> algorithmicModels = new ArrayList<>();
        for(Integer id : ids){

            AlgorithmicModel algorithmicModel = checkAlgorithmicModelInfo(id);
            algorithmicModels.add(algorithmicModel);
            /** 是否绑定算法 */
            checkCorrelationEvent(id);
        }

        Integer delete = algorithmicModelMapper.delete(ids,new Date(),"admin");
        if(delete == 0){
            throw new ServiceException(ErrorCode.DELETE_ALGORITHMIC_MODEL_ERROR);
        }else{
            /** 关闭算法识别 */
            algorithmicModels.forEach(item->{
                LOGGER.debug("nameCode:{},删除算法",item.getNameCode());
                JSONObject jsonReq = JSONUtil.createObj()
                        .putOpt("algorithmCode", item.getNameCode())
                        .putOpt("algorithmReqJsonStr", item.getParameterSample())
                        .putOpt("algorithmUrl", item.getUrl());
                httpManager.algorithmClose(jsonReq);
            });

        }
    }

    @Override
    public PageAlgorithmicModelRespDto pageList(PageAlgorithmicModelReqDto pageAlgorithmicModelReqDto) {
        PageHelper.startPage(pageAlgorithmicModelReqDto.getPageNo(), pageAlgorithmicModelReqDto.getPageSize());
        List<AlgorithmicModel> algorithmicModels = algorithmicModelMapper.queryPageList(pageAlgorithmicModelReqDto);
        List<AlgorithmicModeListItemDto> algorithmicModeListItemDtos = new ArrayList<>();
        if(algorithmicModels != null && algorithmicModels.size() > 0) {
            algorithmicModels.forEach(item -> {
                AlgorithmicModeListItemDto alarmDefineListItemDto = ObjectCovertUtils.doToDto(item, AlgorithmicModeListItemDto.class);
                algorithmicModeListItemDtos.add(alarmDefineListItemDto);
            });
        }

        PageAlgorithmicModelRespDto pageAlarmDefineRespDto = new PageAlgorithmicModelRespDto();
        PageInfo<AlgorithmicModeListItemDto> pageInfo = new PageInfo<>(algorithmicModeListItemDtos);
        pageAlarmDefineRespDto.setCount(pageInfo.getTotal());
        pageAlarmDefineRespDto.setPageCount(pageInfo.getPages());
        pageAlarmDefineRespDto.setList(algorithmicModeListItemDtos);
        return pageAlarmDefineRespDto;
    }

    /**
     * 是否绑定事件
     * @param id
     */
    private void checkCorrelationEvent(Integer id){
        AlgorithmicModel algorithmicModel = algorithmicModelMapper.checkCorrelationEvent(id);
        if(ObjectUtil.isNotEmpty(algorithmicModel)){
            throw new ServiceException(ErrorCode.ALGORITHMIC_BINDING_EVENT_ERROR);
        }
    }

    /**
     * 根据id查找算法模型
     * @param id
     */
    private AlgorithmicModel checkAlgorithmicModelInfo(Integer id){
        AlgorithmicModel algorithmicModel = algorithmicModelMapper.queryById(id);
        if(ObjectUtil.isEmpty(algorithmicModel)){
            throw new ServiceException(ErrorCode.ALGORITHMIC_MODEL_NOT_EXIST);
        }
        return algorithmicModel;
    }

    /**
     * 校验code
     * @param nameCode
     * @return
     */
    private String checkCode(String nameCode){
        String descByCode = dicItemMapper.queryItemNameByItemCode(nameCode);
//        String descByCode = AlgorithmicModelEnum.queryDescByCode(nameCode);
        if(ObjectUtil.isEmpty(descByCode)){
            throw new ServiceException(ErrorCode.ALGORITHMIC_MODEL_CODE_ERROR);
        }
        return descByCode;
    }

    /**
     * 校验算法code是否存在
     * @param nameCode
     */
    public void checkCodeExist(String nameCode){
        AlgorithmicModel algorithmicModel = algorithmicModelMapper.queryByNameCode(nameCode);
        if(ObjectUtil.isNotEmpty(algorithmicModel)){
            throw new ServiceException(ErrorCode.ALGORITHMIC_MODEL_EXIST);
        }

    }

    @Override
    public List<Map<String, Object>> nameList() {
        /** 根据事件类型编码查询事件类型列表 */
        List<DicItem> dicItems = dicItemMapper.queryDicItemList("algorithm_name");
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
    public AlgorithmicListDto algorithmicList() {
        List<DicItem> algorithm_name = dicItemMapper.queryDicItemListByDicCode("algorithm_name");
        Map<String, DicItem> algorithmMap = algorithm_name.stream().collect(Collectors.toMap(DicItem::getItemCode, it -> it));
        List<AlgorithmicItemDto> algorithmicItemDtos = algorithmicModelMapper.algorithmicList();
        algorithmicItemDtos.forEach(it->{
            if(algorithmMap.containsKey(it.getAlgorithmCode())){
                DicItem dicItem = algorithmMap.get(it.getAlgorithmCode());
                it.setAlgorithmName(dicItem.getItemName());
            }
        });
        AlgorithmicListDto algorithmicListDto = new AlgorithmicListDto();
        algorithmicListDto.setAlgorithmicItemDtos(algorithmicItemDtos);
        return algorithmicListDto;
    }

}