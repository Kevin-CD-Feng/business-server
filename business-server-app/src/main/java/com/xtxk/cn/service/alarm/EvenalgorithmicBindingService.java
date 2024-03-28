package com.xtxk.cn.service.alarm;


import com.xtxk.cn.dto.EvenalgorithmicBinding.*;
import com.xtxk.cn.dto.algorithmicModel.PageAlgorithmicModelReqDto;
import com.xtxk.cn.dto.algorithmicModel.PageAlgorithmicModelRespDto;

import java.util.List;
import java.util.Map;

/**
 * @author: lost
 * @description: EvenalgorithmicBinding数据层
 * @date: 2022-10-10 14:02:36
 */
public interface EvenalgorithmicBindingService {

    /**
     * 新增
     * @param addAlgorithmicBindingReqDto
     */
    void add(AddAlgorithmicBindingReqDto addAlgorithmicBindingReqDto);

    /**
     * 列表
     * @param pageAlgorithmicBindingReqDto
     * @return
     */
    PageAlgorithmicBindingRespDto pageList(PageAlgorithmicBindingReqDto pageAlgorithmicBindingReqDto);

    /**
     * 详情
     * @param id
     * @return
     */
    QueryAlgorithmicBindingDetailRespDto detail(Integer id);

    /**
     * 更新
     * @param updateAlgorithmicBindingReqDto
     */
    void update(UpdateAlgorithmicBindingReqDto updateAlgorithmicBindingReqDto);

    /**
     * 删除
     * @param ids
     */
    void delete(Integer[] ids);

    /**
     * 查询可用告警事件
     * @return
     */
    List<Map<String,Object>> eventList();

    List<Map<String, Object>> queryAlgorithmic(String eventCode);
}