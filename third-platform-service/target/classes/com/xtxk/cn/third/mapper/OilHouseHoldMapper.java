package com.xtxk.cn.third.mapper;


import com.xtxk.cn.third.entity.house.OilHouseHold;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OilHouseHoldMapper {
    int deleteByPrimaryKey(String houseHoldInfoId);

    int insert(OilHouseHold record);

    int insertSelective(OilHouseHold record);

    OilHouseHold selectByPrimaryKey(String houseHoldInfoId);

    int updateByPrimaryKeySelective(OilHouseHold record);

    int updateByPrimaryKey(OilHouseHold record);
}