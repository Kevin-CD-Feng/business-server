package com.xtxk.cn.third.mapper;

import com.xtxk.cn.third.entity.user.TPersonFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TPersonFlowMapper {

    int deleteByPrimaryKey(String flowId);

    int insert(TPersonFlow record);

    int insertSelective(TPersonFlow record);

    TPersonFlow selectByPrimaryKey(String flowId);

    int updateByPrimaryKeySelective(TPersonFlow record);

    int updateByPrimaryKey(TPersonFlow record);

    int batchSave(@Param("list") List<TPersonFlow> list);
}