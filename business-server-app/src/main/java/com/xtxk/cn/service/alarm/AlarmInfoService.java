package com.xtxk.cn.service.alarm;


import com.github.pagehelper.PageInfo;
import com.xtxk.cn.dto.AlarmStatusCount;
import com.xtxk.cn.dto.alarmInfo.*;
import com.xtxk.cn.entity.AlarmInfo;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: lost
 * @description: AlarmInfo数据层
 * @date: 2022-10-10 14:02:36
 */
public interface AlarmInfoService {

    /**
     * 获取最近的告警事件
     * @return
     */
    List<AlarmInfo> getLatestAlarmInfo();

    /**
     * 获取当天、当月、当年告警处置情况（1：当天 2：当月 3：当年）
     * @param time
     * @return
     */
    List<AlarmStatusCount> getAlarmDealSit(Integer time);

    /**
     * 根据设备-resourceId获取告警信息
     * @return
     */
    Map<String, List<AlarmLimit3ItemDto>> showAlarmInfos();

    /**
     *  时间类型
     * @param timeType
     * @return
     */
    AreaAlarmCountRespDto getAreaAlarmCountByType(int timeType);

    /**
     *  时间类型
     * @param timeType
     * @return
     */
    AlarmTypeRatioRespDto getAlarmTypeRatioByType(int timeType);

    /**
     * 时间类型
     * @param timeType
     * @return
     */
    AlarmCountRespDto getAlarmCountByType(int timeType);

    /**
     * 多条件查询告警列表(分页)
     * @param alarmQryInfo
     * @return
     */
    PageInfo<AlarmInfo> multiQryAlarmList(AlarmQryInfo alarmQryInfo);

    /**
     * 多条件查询告警列表（不分页）
     * @param alarmQryInfo
     * @return
     */
    List<AlarmInfo> getExportAlarmList(AlarmQryInfo alarmQryInfo);

    /**
     * 查询各状态数量
     * @return
     */
    StatisticalCountRespDto statisticalCount();

    /**
     * 告警中心--分页列表
     * @param pageListReqDto
     * @return
     */
    PageListRespDto pageList(PageListReqDto pageListReqDto);

    /**
     * 获取告警详情
     * @param alarmInfoId
     * @return
     */
    AlarmInfo getAlarmInfo(Integer alarmInfoId);

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    AlarmDetailRespDto queryDetailById(Integer id);

    /**
     * 查询物业人员
     * @return
     */
    PropertyPersonListDto queryPropertyPersonList();



    /**
     * 未核实告警信息更新
     * @param updateWithoutCheckingReqDto
     */
    void updateWithoutChecking(UpdateWithoutCheckingReqDto updateWithoutCheckingReqDto);

    /**
     * 未处置事件更新
     * @param updateNotDisposalReqDto
     */
    void updateNotDisposal(UpdateNotDisposalReqDto updateNotDisposalReqDto);

    /**
     * 查询未核实和未处置告警数量
     * @return
     */
    FlashingRespDto queryCount();

    /**
     * 处理告警信息
     * @param dealAlarmInfoReqDto
     */
    AlarmInfo dealAlarmInfo(DealAlarmInfoReqDto dealAlarmInfoReqDto) throws Exception;


    /**
     * 处理告警信息
     * @param  time
     */
    FlashingRespDto queryAlarmCntByTime(Integer time);

    List<PersonCarAlarmItemDto> getPeronCarAlarmCountByType(String reportType,Date beginDate, Date endDate,Integer vioType);

    List<AlarmTableRspDto> getAlarmTableByTimeType(Integer timeType,String reportType,Date beginDate, Date endDate);

    AreaAlarmCountRespDto getAreaAlarmCountByType2(String resourceType,Date beginDate, Date endDate);

    AlarmTypeRatioRespDto getAlarmTypeRatioByType2(String resourceType,Date beginDate,Date endDate);

    AlarmCountRespDto getAlarmCountByTimeType(String resourceType,Date beginDate,Date endDate);
}