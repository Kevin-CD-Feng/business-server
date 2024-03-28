package com.xtxk.cn.mapper;

import com.xtxk.cn.dto.alarmInfo.FlashingRespDto;
import com.xtxk.cn.entity.AlarmDealInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface AlarmDealInfoMapper {

    /**
     * 新增
     * @param alarmDealInfo
     */
    void insert(AlarmDealInfo alarmDealInfo);

    /**
     * 查询下发信息
     * @param alarmId
     * @return
     */
    List<AlarmDealInfo> queryIssuedInfo(@Param("alarmId") Integer alarmId);

    /**
     * 批量添加下发人员信息
     * @param alarmDealInfos
     */
    void batchInsert(@Param("list")List<AlarmDealInfo> alarmDealInfos);

    /**
     * 根据id查询详情
     * @param detailId
     * @return
     */
    AlarmDealInfo queryByDetailId(@Param("detailId") Integer detailId);

    /**
     * 根据告警信息id查询
     * @param alarmId
     * @return
     */
    AlarmDealInfo queryByAlarmId(@Param("alarmId") Integer alarmId);
}