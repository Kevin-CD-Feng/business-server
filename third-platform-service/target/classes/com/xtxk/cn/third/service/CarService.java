package com.xtxk.cn.third.service;

import com.xtxk.cn.third.dto.car.MockCarInfo;

import java.util.List;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-2210:36
 */
public interface CarService {

    /***
     * 拉取车辆数据
     * @param page
     * @param size
     * @return
     */
    Integer pullCarInfo(Integer page, Integer size);

    /***
     * 同步小区车辆数据
     * @return
     */
    Boolean syncCarInfo();

    /***
     * mock 车辆数据
     * @param mockInfo
     * @return
     */
    Boolean mockCarInfo(List<MockCarInfo> mockInfo);
}
