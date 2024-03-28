package com.xtxk.cn.third.mapper;


import com.xtxk.cn.third.entity.build.OilBuildInformation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OilBuildInformationMapper {

    int deleteByPrimaryKey(String buildId);

    int insert(OilBuildInformation record);

    int insertSelective(OilBuildInformation record);

    OilBuildInformation selectByPrimaryKey(String buildId);

    int updateByPrimaryKeySelective(OilBuildInformation record);

    int updateByPrimaryKey(OilBuildInformation record);

    /***
     * 查询所有楼栋数据
     * @return
     */
    List<OilBuildInformation> selectList();
}