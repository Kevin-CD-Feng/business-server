package com.xtxk.cn.mapper;

import com.xtxk.cn.entity.DeviceVideoRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceVideoRecordMapper {

    int deleteByPrimaryKey(String deviceId);

    int insert(DeviceVideoRecord record);

    int insertSelective(DeviceVideoRecord record);

    DeviceVideoRecord selectByPrimaryKey(String deviceId);

    int updateByPrimaryKeySelective(DeviceVideoRecord record);

    int updateByPrimaryKey(DeviceVideoRecord record);

    List<DeviceVideoRecord> getFailRecord();

    List<DeviceVideoRecord> getNormalRecord();

    List<DeviceVideoRecord> getStopRecord();
}