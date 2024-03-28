package com.xtxk.cn.mapper;

import com.xtxk.cn.dto.AlarmPolicyConfiguration.AlarmPolicyConfigurationItemDto;
import com.xtxk.cn.entity.AlarmPolicyConfiguration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface AlarmPolicyConfigurationMapper {

    /**
     * 根据resourceId删除
     * @param resourceId
     * @return
     */
    void deleteByResourceId(@Param("resourceId") String resourceId);

    /**
     * 批量新增
     * @param alarmPolicyConfigurations
     * @return
     */
    int insert(@Param("list") List<AlarmPolicyConfiguration> alarmPolicyConfigurations);

    /**
     * 设备对应绑定事件列表
     * @param resourceId
     * @return
     */
    List<AlarmPolicyConfiguration> list(@Param("resourceId") String resourceId);

    /**
     * 根据resourceId查询事件设备绑定表
     * @param resourceId
     * @return
     */
    List<AlarmPolicyConfiguration> queryByResourceId(@Param("resourceId") String resourceId);

    /**
     * 根据ids删除
     * @param collectDelete
     */
    void batchDelete(@Param("list") List<AlarmPolicyConfiguration> collectDelete);

    /**
     * 根据ids删除
     * @param collectDelete
     */
    void batchUpdate(@Param("list") List<AlarmPolicyConfiguration> collectDelete);

    /**
     * 查询全部策略配置
     * @return
     */
    List<AlarmPolicyConfiguration> queryAll();


    List<AlarmPolicyConfiguration> queryDevice();

    AlarmPolicyConfiguration queryById(@Param("resourceId") String resourceId);
}