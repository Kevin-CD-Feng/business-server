package com.xtxk.cn.third.mapper;


import com.xtxk.cn.third.entity.car.OilCarInformation;
import com.xtxk.cn.third.entity.user.PersonInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cursor.Cursor;

import java.util.List;

@Mapper
public interface OilCarInformationMapper {

    int deleteByPrimaryKey(String inforPrimaryId);

    int insert(OilCarInformation record);

    int insertSelective(OilCarInformation record);

    OilCarInformation selectByPrimaryKey(String inforPrimaryId);

    int updateByPrimaryKeySelective(OilCarInformation record);

    int updateByPrimaryKey(OilCarInformation record);

    List<OilCarInformation> selectList();

    Cursor<OilCarInformation> streamQueryCarList();


}