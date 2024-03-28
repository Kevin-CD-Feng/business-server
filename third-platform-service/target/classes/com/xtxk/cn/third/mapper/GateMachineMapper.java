package com.xtxk.cn.third.mapper;

import com.xtxk.cn.third.entity.gate.GateMachine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GateMachineMapper {

    int deleteByPrimaryKey(Integer gateMachineId);

    int insert(GateMachine record);

    int insertSelective(GateMachine record);

    GateMachine selectByPrimaryKey(Integer gateMachineId);

    int updateByPrimaryKeySelective(GateMachine record);

    int updateByPrimaryKey(GateMachine record);

    /***
     * 查询所有门禁数据
     * @return
     */
    List<GateMachine> selectList();

    /***
     * 批量保存门禁数据
     * @param list
     * @return
     */
    int batchSave(@Param("list") List<GateMachine> list);

    /**
     * 批量更新门禁数据
     * @param list
     * @return
     */
    int updateBatchSave(@Param("list") List<GateMachine> list);

    /***
     * @param ids
     * @return
     */
    int deleteByIds(@Param("list") List<Integer> ids);
}