package com.xtxk.cn.third.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-24 14:39
 */
@Component
public class SampleXxlJob {

    private static final Logger logger = LoggerFactory.getLogger(SampleXxlJob.class);

    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;
    @Autowired
    private BuildService buildService;
    @Autowired
    private HouseService houseService;

    @XxlJob("UserJobHandler")
    public void userJobHandler() {
        logger.info("开始拉取小区人员数据------------->");
        userService.pullUserInfo(0, 200);
        userService.syncUserInfo();
        logger.info("拉取小区人员数据结束------------->");
    }

    @XxlJob("CarJobHandler")
    public void carJobHandler() {
        logger.info("开始拉取小区车辆数据------------->");
        carService.pullCarInfo(0, 200);
        carService.syncCarInfo();
        logger.info("拉取小区车辆数据结束------------->");
    }

    @XxlJob("BuildJobHandler")
    public void buildJobHandler() {
        logger.info("开始拉取小区楼栋数据------------->");
        buildService.pullBuildInfo(0, 200);
        buildService.syncBuild();
        logger.info("拉取小区楼栋数据结束------------->");

        logger.info("开始拉取小区房屋数据------------->");
        houseService.pullHouseInfo(0, 200);
        houseService.syncHouse();
        logger.info("拉取小区房屋数据结束------------->");

        logger.info("开始拉取小区房屋住户数据------------->");
        houseService.pullHouseHoldInfo(0,200);
        logger.info("拉取小区房屋住户数据结束------------->");
    }

}
