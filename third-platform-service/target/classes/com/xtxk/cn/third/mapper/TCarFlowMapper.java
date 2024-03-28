package com.xtxk.cn.third.mapper;

import com.xtxk.cn.third.entity.car.TCarFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TCarFlowMapper {

    int deleteByPrimaryKey(String flowId);

    int insert(TCarFlow record);

    int insertSelective(TCarFlow record);

    TCarFlow selectByPrimaryKey(String flowId);

    int updateByPrimaryKeySelective(TCarFlow record);

    int updateByPrimaryKey(TCarFlow record);

    /***
     * 批量保存车流量
     * @param list
     * @return
     */
    int batchSave(@Param("list") List<TCarFlow> list);
}