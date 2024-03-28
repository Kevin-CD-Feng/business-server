package com.xtxk.cn.service.impl.video;

import com.xtxk.cn.entity.AlarmPolicyConfiguration;
import com.xtxk.cn.entity.DeviceVideoRecord;
import com.xtxk.cn.service.video.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class VideoRecordRunner {

    @Autowired
    private VideoService videoService;
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoRecordRunner.class);

//    /***
//     * 每3分钟执行
//     */
//    @Scheduled(cron = "0 0/3 * * * ?")
//    private void failVideoListener() {
//        List<DeviceVideoRecord> failList = videoService.getFailRecord();
//        LOGGER.info("定时监听录像失败设备数:{}", failList.size());
//        if (failList.size() > 0) {
//            for (DeviceVideoRecord device : failList) {
//                try {
//                    videoService.stopVideo(device.getDeviceId());
//                    Thread.sleep(5000);
//                    videoService.startVideo(device.getDeviceId(), device.getDeviceName(), device.getDeviceSipId());
//                } catch (Exception e) {
//                    LOGGER.error("初始化设备录像异常: 设备id {}, 设备名称：{}", device.getDeviceId(), device.getDeviceName());
//                }
//            }
//        }
//    }

//    /***
//     * 每天23点55分执行录像重启
//     */
//    @Scheduled(cron = "0 55 23 ? * *")
//    private void reStartVideoListener() {
//        List<DeviceVideoRecord> deviceList = videoService.getNormalRecord();
//        LOGGER.info("定时监听录像开启设备数:{}", deviceList.size());
//        if (deviceList.size() > 0) {
//            for (DeviceVideoRecord device : deviceList) {
//                try {
//                    videoService.stopVideo(device.getDeviceId());
//                    Thread.sleep(5000);
//                    videoService.startVideo(device.getDeviceId(), device.getDeviceName(), device.getDeviceSipId());
//                } catch (Exception e) {
//                    LOGGER.error("开启设备录像异常: 设备id {}, 设备名称：{}", device.getDeviceId(), device.getDeviceName());
//                }
//            }
//        }
//    }

//    /***
//     * 每天23点58执行开启录像
//     */
//    @Scheduled(cron = "0 58 23 ? * *")
//    private void startVideoListener() {
//        List<DeviceVideoRecord> deviceList = videoService.getStopRecord();
//        LOGGER.info("定时监听录像关闭设备数:{}", deviceList.size());
//        if (deviceList.size() > 0) {
//            for (DeviceVideoRecord device : deviceList) {
//                try {
//                    videoService.startVideo(device.getDeviceId(), device.getDeviceName(), device.getDeviceSipId());
//                    Thread.sleep(1000);//休眠1s
//                } catch (Exception e) {
//                    LOGGER.error("开启设备录像异常: 设备id {}, 设备名称：{}", device.getDeviceId(), device.getDeviceName());
//                }
//            }
//        }
//    }

   /* @Override
    public void run(String... args) {
        // 开启设备录像
        List<AlarmPolicyConfiguration> monitorList = videoService.getAllDevice();
        // 未开启录制的设备开始录像
        monitorList = monitorList.stream().filter(m-> m.getDeleted().equals(0)).collect(Collectors.toList());
        if (monitorList != null && monitorList.size() > 0) {
            for (AlarmPolicyConfiguration device : monitorList) {
                try {
                    videoService.startVideo(device.getResourceId(), device.getEventCode(),device.getWebCoordinate());
                } catch (Exception e) {
                    LOGGER.error("初始化设备录像异常: 设备id {}, 设备名称：{}", device.getResourceId(), device.getEventCode());
                }
            }
        }
    }*/
}