package com.xtxk.recognition.prepare.service.svc;

import com.xtxk.recognition.prepare.service.dto.dic.*;

import java.util.List;

public interface DicService {

    /**
     * 字典列表
     * @return
     */
    DicListRespDto list(DicListReqDto dicListReqDto);

    /**
     * 字典详情
     * @param id
     * @return
     */
    DicDetailRespDto detail(Integer id);

    /**
     * 字典元素下拉列表
     * @return
     */
    DicSuperiorListRespDto superiorList();

    /**
     *
     * @param dicAddReqDto
     */
    void add(DicAddReqDto dicAddReqDto);

    /**
     * 字典元素更新
     * @param dicUpdateReqDto
     */
    void update(DicUpdateReqDto dicUpdateReqDto);

    /**
     * 根据dicId查询字典项
     * @param dicParentCode
     * @return
     */
    DictionaryEntryRespDto dictionaryEntryList(String dicParentCode);


    /**
     * 获取所有的EventDictItem
     * @return
     */
    List<EventDictItemDto>  queryEventDictItem();
}
