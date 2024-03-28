package com.xtxk.cn.mapper;

import com.xtxk.cn.dto.patrolstrategy.GroupDeviceDTO;
import com.xtxk.cn.entity.GroupDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupDeviceMapper {

    int deleteByPrimaryKey(String groupId);

    int insert(GroupDevice record);

    int insertSelective(GroupDevice record);

    GroupDevice selectByPrimaryKey(String groupId);

    int updateByPrimaryKeySelective(GroupDevice record);

    int updateByPrimaryKey(GroupDevice record);

    int insertBatch(@Param("list") List<GroupDeviceDTO> list);

    int deleteByStrategyId(@Param("strategyId") String strategyId);


    List<GroupDeviceDTO> selectAll();


}