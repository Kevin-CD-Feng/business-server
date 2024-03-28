package com.xtxk.cn.service.alarm;


import com.xtxk.cn.dto.alarmDefine.PageAlarmDefineReqDto;
import com.xtxk.cn.dto.algorithmicModel.*;

import java.util.List;
import java.util.Map;

/**
 * @author: lost
 * @description: AlgorithmicModel数据层
 * @date: 2022-10-10 14:02:36
 */
public interface AlgorithmicModelService {

    /**
     * 新增算法模型
     * @param addAlgorithmicModelReqDto
     */
    void add(AddAlgorithmicModelReqDto addAlgorithmicModelReqDto);

    /**
     * 根据id查询详情
     * @return
     */
    DetailAlgorithmicModelRespDto detail(Integer id);

    /**
     * 更新算法模型
     * @param updateAlgorithmicModelReqDto
     */
    void update(UpdateAlgorithmicModelReqDto updateAlgorithmicModelReqDto);

    /**
     * 删除
     * @param ids
     */
    void delete(Integer[] ids);

    /**
     * 分页查询
     * @param pageAlgorithmicModelReqDto
     * @return
     */
    PageAlgorithmicModelRespDto pageList(PageAlgorithmicModelReqDto pageAlgorithmicModelReqDto);

    /**
     * 校验算法code是否存在
     * @param nameCode
     */
    void checkCodeExist(String nameCode);

    List<Map<String, Object>> nameList();

    /**
     * 查询全部可用算法
     * @return
     */
    AlgorithmicListDto algorithmicList();
}