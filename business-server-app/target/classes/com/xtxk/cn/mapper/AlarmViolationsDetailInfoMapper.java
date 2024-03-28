package com.xtxk.cn.mapper;

import com.xtxk.cn.entity.AlarmViolationsDetailInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface AlarmViolationsDetailInfoMapper {

    /**
     * 批量插入
     * @param alarmViolationsDetailInfos
     */
    void insertBatch(@Param("list") List<AlarmViolationsDetailInfo> alarmViolationsDetailInfos);

    void updateBuildingViolationInfo(@Param("floorNumber")String floorNumber,@Param("houseNumber")String houseNumber,@Param("targetObjId")String targetObjId);
}