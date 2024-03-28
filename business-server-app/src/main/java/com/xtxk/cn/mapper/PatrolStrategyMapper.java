package com.xtxk.cn.mapper;

import com.xtxk.cn.dto.patrolstrategy.Params;
import com.xtxk.cn.dto.patrolstrategy.PatrolStrategyDTO;
import com.xtxk.cn.entity.PatrolStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PatrolStrategyMapper {

    int deleteByPrimaryKey(String strategyId);

    int insert(PatrolStrategy record);

    int insertSelective(PatrolStrategy record);

    PatrolStrategy selectByPrimaryKey(String strategyId);

    int updateByPrimaryKeySelective(PatrolStrategy record);

    int updateByPrimaryKey(PatrolStrategy record);

    /***
     * 根据名称查询巡查方案
     * @param strategyName
     * @return
     */
    PatrolStrategy queryByStrategyName(@Param("strategyName") String strategyName);

    List<PatrolStrategyDTO> queryList(Params params);
}