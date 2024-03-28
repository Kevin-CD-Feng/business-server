package com.xtxk.cn.mapper;

import com.xtxk.cn.dto.AlarmStatusCount;
import com.xtxk.cn.dto.AlarmTypeCount;
import com.xtxk.cn.dto.alarmInfo.*;
import com.xtxk.cn.dto.alarmInfo.AlarmCountItemDto;
import com.xtxk.cn.dto.alarmInfo.AlarmQryInfo;
import com.xtxk.cn.dto.alarmInfo.AlarmTypeItemDto;
import com.xtxk.cn.dto.alarmInfo.AreaItemDto;
import com.xtxk.cn.dto.geogra.GeograObjDto;
import com.xtxk.cn.dto.statistic.event.*;
import com.xtxk.cn.entity.AlarmInfo;
import com.xtxk.cn.entity.AlarmViolationsDetailInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;


@Mapper
public interface AlarmInfoMapper {

    /**
     * 获取最近的未处置和处置中的告警事件
     * @return
     */
    List<AlarmInfo> getLatestUndealedOrDealing();

    /**
     * 获取当天、当月、当年的告警总量（1：当天 2：当月 3：当年）
     * @param time
     * @return
     */
    Integer getTotalByTime(@Param("time") Integer time);

    /**
     * 按照处置状态获取当天、当月、当年的告警数量
     * @param time
     * @return
     */
    List<AlarmStatusCount> getAlarmStatusCountByTime(@Param("time")Integer time);

    /**
     * 根据设备resourceId获取告警事件
     * @param resourceId
     * @return
     */
    List<AlarmInfo> getAlarmByResourceId(@Param("resourceId") String resourceId);

    /**
     * 获取各种告警类型对应的告警数量
     * @return
     */
    List<AlarmTypeCount> getAlarmTypeCount();


    /**
     * 区域
     * @param time
     * @return
     */
    List<AreaItemDto> getAreaAlarmCountByType(@Param("time")Integer time);

    List<AreaItemDto> getAreaAlarmCountByType2(@Param("resourceType") String resourceType,
                                              @Param("beginDate") Date beginDate,
                                              @Param("endDate") Date endDate);

    /**
     * 类型
     * @param time
     * @return
     */
    List<AlarmTypeItemDto> getAlarmTypeRatioByType(@Param("time")Integer time);
    List<AlarmTypeItemDto> getAlarmTypeRatioByType2(@Param("resourceType") String resourceType,
                                               @Param("beginDate") Date beginDate,
                                               @Param("endDate") Date endDate);

    /**
     * 告警数量
     * @param now
     * @return
     */
    List<AlarmCountItemDto> getAlarmCountByHour(@Param("now") Date now);
    List<AlarmCountItemDto> getAlarmCountByDay(@Param("now") Date now);
    List<AlarmCountItemDto> getAlarmCountByMonth(@Param("now") Date now);
    List<AlarmCountItemDto> getAlarmCountByTimeType(@Param("resourceType") String resourceType,
                                                   @Param("format") String format,
                                                   @Param("beginDate") Date beginDate,
                                                   @Param("endDate") Date endDate);

    /**
     * 多条件查询告警列表
     * @param alarmQryInfo
     * @return
     */
    List<AlarmInfo> multiQryAlarmList(AlarmQryInfo alarmQryInfo);

    /**
     * 查询各状态数量
     * @return
     */
    StatisticalCountRespDto statisticalCount();

    /**
     * 告警中心-分页列表
     * @param pageListReqDto
     * @return
     */
    List<AlarmInfo> pageList(@Param("param")PageListReqDto pageListReqDto);

    /**
     * 获取地理信息结果
     * @return
     */
    List<GeograObjDto> getGeograObjDto();

    /**
     * 通过id获取告警信息
     * @param alarmInfoId
     * @return
     */
    AlarmInfo getById(@Param("param")Integer alarmInfoId);

    /**
     * 根据经纬度分组获取最近三条告警信息
     * @return
     */
    List<AlarmInfoByJingWeiDto> getAlarmInfosGroupAndLimit3();

    /**
     * 根据id查询告警详情
     * @param id
     * @return
     */
    AlarmInfo queryDetailById(@Param("id") Integer id);

    /**
     * 查询告警事件描述
     * @param alarmId
     * @return
     */
    String queryEventDesc(@Param("alarmId") Integer alarmId);

    /**
     * 查询违法信息
     * @param alarmId
     * @return
     */
    List<AlarmViolationsDetailInfo> queryViolationsInfo(@Param("alarmId") Integer alarmId);

    /**
     * 根据id更新字段
     * @param alarmInfo
     */
    void updateById(AlarmInfo alarmInfo);

    /**
     * 查询未核实和未处理
     * @return
     */
    FlashingRespDto queryCount();

    /**
     * 新增告警信息
     * @param alarmInfo
     * @return
     */
    Integer insert(@Param("alarmInfo") AlarmInfo alarmInfo);

    /**
     * 获取各设备的历史告警总数
     * @return
     */
    List<DevHisAlarmCount> getDevHisAlarmCount();

    AlarmObjectDto queryPersonVioByAlarmId(@Param("alarmId") Integer alarmId);

    AlarmObjectDto queryCarVioByAlarmId(@Param("alarmId") Integer alarmId);

    AlarmObjectDto queryBuildingByAlarmId(@Param("alarmId") Integer alarmId);

    Integer queryVioCnt(@Param("alarmId")Integer alarmId,@Param("objectId")String objectId,@Param("beginDate")Date beginDate,@Param("endDate")Date endDate);

    List<AlarmDealInfoRspDto> queryAlarmStatusByAlarmId(@Param("alarmId")Integer alarmId);

    /***
     * 统计告警处理状态
     * @return
     */
    List<AlarmStatusCount> statisticStatus(ParamDTO paramDTO);

    /***
     * 统计分析-事件类型处理统计
     * @return
     */
    List<EventHandleDTO> statisticTypeHandle(ParamDTO paramDTO);

    /***
     * 统计分析-告警处理统计
     * @return
     */
    List<HandVo> statistician(ParamDTO paramDTO);

    /***
     * 统计分析-列表数据
     * @param
     * @return
     */
    List<EventHandleDTO> statisticTypeHandle(@Param("eventSource") Integer eventSource,
                                             @Param("beginTime") Date startTime,
                                             @Param("endTime") Date endTime);


   List<AlarmTableDto> getAlarmTableByTimeType(@Param("resourceType") String resourceType,
                            @Param("format") String format,
                            @Param("beginDate") Date beginDate,
                            @Param("endDate") Date endDate);

    List<PersonCarAlarmItemDto> getPeronCarAlarmCountByType(@Param("resourceType") String resourceType,
                                                            @Param("vioType") Integer vioType,
                                                @Param("beginDate") Date beginDate,
                                                @Param("endDate") Date endDate);

    List<TableDataVo> staticTable(ParamDTO eventParam);


    Integer getAlarmIdByImageUrl(String imageUrl);

}