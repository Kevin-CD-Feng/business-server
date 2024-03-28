package com.xtxk.cn.third.mapper;


import com.xtxk.cn.third.entity.house.OilHouseInformation;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OilHouseInformationMapper {

    int deleteByPrimaryKey(String householdInfoId);

    int insert(OilHouseInformation record);

    int insertSelective(OilHouseInformation record);

    OilHouseInformation selectByPrimaryKey(String householdInfoId);

    int updateByPrimaryKeySelective(OilHouseInformation record);

    int updateByPrimaryKey(OilHouseInformation record);

    /***
     * 查询全部房屋数据
     * @return
     */
    List<OilHouseInformation> selectList();
}