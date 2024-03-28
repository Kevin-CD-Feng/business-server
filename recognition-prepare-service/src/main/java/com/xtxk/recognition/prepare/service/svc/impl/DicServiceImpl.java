package com.xtxk.recognition.prepare.service.svc.impl;

import cn.hutool.core.util.ObjectUtil;
import com.xtxk.recognition.prepare.service.dto.dic.*;
import com.xtxk.recognition.prepare.service.entity.Dic;
import com.xtxk.recognition.prepare.service.entity.DicItem;
import com.xtxk.recognition.prepare.service.enums.ErrorCode;
import com.xtxk.recognition.prepare.service.exception.ServiceException;
import com.xtxk.recognition.prepare.service.mapper.DicItemMapper;
import com.xtxk.recognition.prepare.service.mapper.DicMapper;
import com.xtxk.recognition.prepare.service.svc.DicService;
import com.xtxk.recognition.prepare.service.utils.ObjectCovertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DicServiceImpl implements DicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DicServiceImpl.class);

    @Autowired
    private DicMapper dicMapper;

    @Autowired
    private DicItemMapper dicItemMapper;

    @Override
    public DicListRespDto list(DicListReqDto dicListReqDto) {
        List<DicParentItemDto> list = dicMapper.list(dicListReqDto.getDicName());
        DicListRespDto dicListRespDto = new DicListRespDto();
        if(list !=null && list.size() > 0){
            dicListRespDto.setDicParentItemDtos(list);
        }
        return dicListRespDto;
    }

    @Override
    public DicDetailRespDto detail(Integer id) {
        /** 查询字典元素详情 */
        Dic detail = checkExist(id);
        DicDetailRespDto dicDetailRespDto = ObjectCovertUtils.doToDto(detail, DicDetailRespDto.class);

        /** 查询字典元素下的子项列表 */
        List<DicAddItemDto> dicItemDtos = dicMapper.queryItemsByDicId(id);
        dicDetailRespDto.setDicItemDtos(dicItemDtos);
        return dicDetailRespDto;
    }

    @Override
    public DicSuperiorListRespDto superiorList() {
        /** 查询字典元素-一级目录 */
        List<SuperiorItemDto> superiorItemDtos = dicMapper.superiorList();
        DicSuperiorListRespDto dicSuperiorListRespDto = new DicSuperiorListRespDto();
        dicSuperiorListRespDto.setSuperiorItemDtos(superiorItemDtos);
        return dicSuperiorListRespDto;
    }

    @Override
    @Transactional
    public void add(DicAddReqDto dicAddReqDto) {

        /** 校验code是否存在 */
        String dicCode = dicAddReqDto.getDicCode();
        checkCodeUnique(dicCode);


        /** 字典元素添加 */
        Integer dicId = AddDic(dicAddReqDto);

        /** 字典项添加 */
        addDicItem(dicAddReqDto,dicId);

    }

    /**
     * 校验code是否存在
     * @param dicCode
     */
    private void checkCodeUnique(String dicCode){
        String dicCodeExist = dicMapper.checkCodeUnique(dicCode);
        if(ObjectUtil.isNotEmpty(dicCodeExist)){
            throw new ServiceException(ErrorCode.DIC_CODE_EXIST);
        }
    }

    @Override
    @Transactional
    public void update(DicUpdateReqDto dicUpdateReqDto) {
        /** 根据字典元素id校验是否存在 */
        checkExist(dicUpdateReqDto.getId());

        /** 字典元素更新 */
        updateDic(dicUpdateReqDto);

        /** 字典项处理 */
        /** 判断集合是否为空 */
        List<DicItemDto> dicItemDtos = dicUpdateReqDto.getDicItemDtos();
        if(dicItemDtos != null && dicItemDtos.size() > 0){
            /** 将新增字典项筛选 */
            List<DicItemDto> collectNew = filterAdd(dicItemDtos);
            /** 获取剔除新增字典项数据集的集合 */
            List<DicItemDto> collectOther =dicItemDtos.stream().filter(item -> ObjectUtil.isNotEmpty(item.getId())).collect(Collectors.toList());
            /** 获取当前数据库字典元素下的字典项集合 */
            List<DicItem> dicItems = dicItemMapper.queryDicItemListByDicId(dicUpdateReqDto.getId());
            /** 筛选删除字典项 */
            List<DicItem> collectDelete = filterDelete(collectOther,dicItems);
            /** 获取当前数据库除删除字典项的数据，用于判断剩下的数据中哪些是需要做更新操作 */
            List<Integer> collect1 = dicItemDtos.stream().map(e -> e.getId()).collect(Collectors.toList());
            List<DicItem> collect = dicItems.stream().filter(item -> collect1
                    .contains(item.getId()))
                    .collect(Collectors.toList());
            /** 最后是更新字典项 */
            List<DicItemDto> collectUpdate = filterUpdate(collectOther,collect);

            /** 特殊判断删除字典项和更新字典项是否绑定算法和设备  如果绑定就抛出异常 */
            /** 字典项sql操作-新增  删除  修改 */
            /** 新增 */
            batchAdd(collectNew,dicUpdateReqDto.getId());
            /** 删除 */
            batchDeleted(collectDelete);
            /** 修改 */
            batchUpdate(collectUpdate);

        }else{
            /** 根据字典元素删除所有子项 */
            Integer id = dicUpdateReqDto.getId();
            List<DicItem> dicItems = dicItemMapper.queryDicItemListByDicId(id);
            batchDeleted(dicItems);
        }
    }

    /**
     * 批量修改修改
     * @param collectUpdate
     */
    private void batchUpdate(List<DicItemDto> collectUpdate){
        if(collectUpdate != null && collectUpdate.size() > 0) {
            List<DicItem> dicItems = new ArrayList<>();
            collectUpdate.forEach(item -> {
                DicItem dicItem = ObjectCovertUtils.dtoToDo(item, DicItem.class);
                dicItems.add(dicItem);
            });
            dicItemMapper.batchUpdateByIds(dicItems);
        }else{
            LOGGER.debug("暂无需要更新数据");
        }
    }

    /**
     * 数据批量新增
     * @param collectNew
     */
    private void batchAdd(List<DicItemDto> collectNew,Integer dicId){
        if(collectNew!=null && collectNew.size() > 0) {
            List<DicItem> dicItems = new ArrayList<>();
            collectNew.forEach(item -> {
                String itemCode = item.getItemCode();
                checkItemCodeUnique(itemCode);
                DicItem dicItem = ObjectCovertUtils.dtoToDo(item, DicItem.class);
                dicItem.setDeleted(0);
                dicItem.setDicId(dicId);
                dicItems.add(dicItem);
            });
            dicItemMapper.batchAdd(dicItems);
        }
    }

    /**
     * 批量删除字典项
     * @param collectDelete
     */
    private void batchDeleted(List<DicItem> collectDelete){
        if(collectDelete != null && collectDelete.size() > 0) {
            List<String> collect1 = collectDelete.stream().map(e -> e.getItemCode()).collect(Collectors.toList());
            /** 查询删除字典项下级数据 */
            List<DicItem> existDicItem = dicItemMapper.queryDicItemListByItemCodes(collect1);
            if(existDicItem != null && existDicItem.size() > 0){
                throw new ServiceException(ErrorCode.DIC_EMPTY);
            }
            /** 根据itemCodes逻辑删除 */
            dicItemMapper.deleteByItemCodes(collect1);
        }else{
            LOGGER.debug("暂无需要删除字典项");
        }


    }


    @Override
    public DictionaryEntryRespDto dictionaryEntryList(String dicParentCode) {
        List<DicItem> dicItems = dicItemMapper.queryDicItemListByDicCode(dicParentCode);
        DictionaryEntryRespDto dictionaryEntryRespDto = new DictionaryEntryRespDto();
        if(dicItems != null && dicItems.size() > 0){
            List<DictionaryEntryItemDto> dictionaryEntryItemDtos = new ArrayList<>();
            dicItems.forEach(item -> {
                DictionaryEntryItemDto dictionaryEntryItemDto = new DictionaryEntryItemDto();
                dictionaryEntryItemDto.setItemParentCode(item.getItemCode());
                dictionaryEntryItemDto.setItemParentName(item.getItemName());
                dictionaryEntryItemDtos.add(dictionaryEntryItemDto);
            });
            dictionaryEntryRespDto.setDictionaryEntryItemDtos(dictionaryEntryItemDtos);
        }
        return dictionaryEntryRespDto;
    }

    @Override
    public List<EventDictItemDto> queryEventDictItem() {
        return this.dicItemMapper.queryEventDictItem();
    }

    /**
     * 根据id和Item_desc比较获取更新数据
     * @param dicItemDtos
     * @param dicItems
     * @return
     */
    private List<DicItemDto> filterUpdate(List<DicItemDto> dicItemDtos,List<DicItem> dicItems){

        List<DicItemDto> notUpdate = new ArrayList<>();
        for(int i = 0; i < dicItemDtos.size(); i++){
            for(int j =0; j <dicItems.size(); j++){
                DicItemDto o = dicItemDtos.get(i);
                DicItem b = dicItems.get(j);
                if(o.getId() == b.getId() && o.getItemDesc().equals(b.getItemCode()) && o.getEnable() == b.getEnable()){
                    notUpdate.add(o);
                }
            }
        }

        List<Integer> collect1 = notUpdate.stream().map(e -> e.getId()).collect(Collectors.toList());
        return dicItemDtos.stream().filter(item -> !collect1
                .contains(item.getId()))
                .collect(Collectors.toList());
    }

    /**
     * 筛选新增字典项
     * @param dicItemDtos
     * @return
     */
    private List<DicItemDto> filterAdd(List<DicItemDto> dicItemDtos){
        return dicItemDtos.stream().filter(item -> ObjectUtil.isEmpty(item.getId())).collect(Collectors.toList());
    }

    /**
     * 筛选删除字典项
     * @param dicItemDtos
     * @return
     */
    private List<DicItem> filterDelete(List<DicItemDto> dicItemDtos,List<DicItem> dicItems){
        List<Integer> collect1 = dicItemDtos.stream().map(e -> e.getId()).collect(Collectors.toList());
        return dicItems.stream().filter(item -> !collect1
                .contains(item.getId()))
                .collect(Collectors.toList());
    }

    /**
     * 更新字典元素
     * @param dicUpdateReqDto
     */
    private void updateDic(DicUpdateReqDto dicUpdateReqDto){
        Dic update = new Dic();
        update.setId(dicUpdateReqDto.getId());
        update.setDicDesc(dicUpdateReqDto.getDicDesc());
        dicMapper.updateDic(update);
    }

    /**
     * 根据id查询字典元素
     * @param id
     * @return
     */
    private Dic checkExist(Integer id){
        Dic dic = dicMapper.queryById(id);
        if(ObjectUtil.isEmpty(dic)){
            throw new ServiceException(ErrorCode.DIC_EMPTY);
        }
        return dic;
    }

    /**
     * 字典元素添加
     * @param dicAddReqDto
     */
    private Integer AddDic(DicAddReqDto dicAddReqDto){
        /** 查询上级字典元素 */
        Dic dic = new Dic();
        dic.setDicParentCode(dicAddReqDto.getDicParentCode());
        dic.setDeleted(0);
        dic.setDicCode(dicAddReqDto.getDicCode());
        dic.setDicDesc(dicAddReqDto.getDicDesc());
        dic.setDicName(dicAddReqDto.getDicName());
        dicMapper.add(dic);
        return dic.getId();
    }

    /**
     * 字典项添加
     * @param dicAddReqDto
     */
    private void addDicItem(DicAddReqDto dicAddReqDto,Integer dicId){
        List<DicAddItemDto> dicItemDtos = dicAddReqDto.getDicItemDtos();
        if(dicItemDtos!=null && dicItemDtos.size() > 0){
            List<DicItem> dicItems = new ArrayList<>();
            dicItemDtos.forEach(item -> {

                String itemCode = item.getItemCode();
                checkItemCodeUnique(itemCode);

                DicItem dicItem = ObjectCovertUtils.dtoToDo(item, DicItem.class);
                dicItem.setDeleted(0);
                dicItem.setDicId(dicId);
                dicItems.add(dicItem);
            });
            dicItemMapper.batchAdd(dicItems);
        }
    }

    private void checkItemCodeUnique(String itemCode){
        String itemCodeExist = dicItemMapper.checkItemCodeUnique(itemCode);
        if(ObjectUtil.isNotEmpty(itemCodeExist)){
            throw new ServiceException(ErrorCode.DIC__ITEM_CODE_EXIST);
        }
    }
}
