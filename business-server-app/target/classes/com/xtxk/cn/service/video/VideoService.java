package com.xtxk.cn.service.video;

import com.xtxk.cn.dto.video.VideoReq;
import com.xtxk.cn.entity.AlarmPolicyConfiguration;
import com.xtxk.cn.entity.DeviceVideoRecord;
import com.xtxk.cn.entity.MonitorDevicePO;

import java.util.List;

/***
 * @description 录像相关
 * @author liulei
 * @date 2023-08-07 9:35
 */
public interface VideoService {

    Boolean startVideo(String resourceId,String resourceName,String deviceSipId);

    Boolean statusRepay(VideoReq videoReq);

    List<AlarmPolicyConfiguration> getAllDevice();

    List<DeviceVideoRecord> getFailRecord();

    Boolean stopVideo(String resourceId);

    List<DeviceVideoRecord> getNormalRecord();

    List<DeviceVideoRecord> getStopRecord();

}
