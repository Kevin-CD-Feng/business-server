package com.xtxk.recognition.prepare.service.mapper;

import com.xtxk.recognition.prepare.service.dto.algorithmicModel.AlgorithmicItemDto;
import com.xtxk.recognition.prepare.service.dto.algorithmicModel.PageAlgorithmicModelReqDto;
import com.xtxk.recognition.prepare.service.entity.AlgorithmicModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface AlgorithmicModelMapper {

    /**
     * 通过算法模型code查找
     * @param nameCode
     * @return
     */
    AlgorithmicModel queryByNameCode(@Param("nameCode") String nameCode);

    /**
     * 新增
     * @param algorithmicModel
     */
    Integer insert(AlgorithmicModel algorithmicModel);

    /**
     * 通过算法模型id查找
     * @param id
     * @return
     */
    AlgorithmicModel queryById(@Param("id") Integer id);

    /**
     * 更新
     * @param algorithmicModel
     * @return
     */
    Integer update(AlgorithmicModel algorithmicModel);

    /**
     * 查询绑定事件
     * @param id
     * @return
     */
    AlgorithmicModel checkCorrelationEvent(@Param("id") Integer id);

    /**
     * 删除
     * @param ids
     * @return
     */
    Integer delete(@Param("ids") Integer[] ids, @Param("updateTime") Date updateTime, @Param("updateUser") String updateUser);

    /**
     * 查询列表
     * @param pageAlgorithmicModelReqDto
     * @return
     */
    List<AlgorithmicModel> queryPageList(@Param("param") PageAlgorithmicModelReqDto pageAlgorithmicModelReqDto);

    /**
     * 查询全部算法供调度系统使用
     * @return
     */
    List<AlgorithmicItemDto> algorithmicList();
}