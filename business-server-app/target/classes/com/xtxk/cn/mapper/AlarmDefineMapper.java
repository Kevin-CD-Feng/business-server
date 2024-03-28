package com.xtxk.cn.mapper;

import com.xtxk.cn.dto.alarmDefine.AlarmDefineItemDto;
import com.xtxk.cn.dto.alarmDefine.PageAlarmDefineReqDto;
import com.xtxk.cn.dto.alarmDefine.PageAlarmDefineRespDto;
import com.xtxk.cn.entity.AlarmDefine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


@Mapper
public interface AlarmDefineMapper {

    /**
     * 校验eventCode否存在
     * @param eventCode
     * @return
     */
    AlarmDefine checkEventCode(@Param("eventCode") String eventCode);

    /**
     * 新增
     * @param alarmDefine
     */
    void insert(AlarmDefine alarmDefine);

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    AlarmDefine queryById(@Param("id") Integer id);

    /**
     * 更新
     */
    Integer update(AlarmDefine alarmDefine);

    /**
     * 是否关联设备
     * @param id
     * @return
     */
    AlarmDefine checkCorrelationDevice(@Param("id") Integer id);

    /**
     * 是否绑定算法
     * @param id
     * @return
     */
    AlarmDefine checkCorrelationAlgorithmic(@Param("id") Integer id);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer delete(@Param("ids") Integer[] id, @Param("updateTime")Date updateTime, @Param("updateUser") String updateUser);

    /**
     * 查询列表
     * @param pageAlarmDefineReqDto
     * @return
     */
    List<AlarmDefine> queryPageList(@Param("param")PageAlarmDefineReqDto pageAlarmDefineReqDto);

    /**
     *  根据eventCode查询处置信息
     * @param eventCode
     */
    AlarmDefine queryDisposalFlag(@Param("eventCode")String eventCode);
}