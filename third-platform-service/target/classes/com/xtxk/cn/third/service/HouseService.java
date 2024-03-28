package com.xtxk.cn.third.service;

import com.xtxk.cn.third.dto.house.MockHouse;
import java.util.List;

/***
 * @description 出租屋
 * @author liulei
 * @date 2023-08-22 10:37
 */
public interface HouseService {

    /***
     * 拉取出租屋数据
     * @param page
     * @param size
     * @return
     */
    Integer pullHouseInfo(Integer page, Integer size);

    /***
     *  同步出租屋数据
     * @return
     */
    Boolean syncHouse();

    /***
     * 拉取房屋住户数据
     * @param page
     * @param size
     * @return
     */
    Integer pullHouseHoldInfo(Integer page, Integer size);

    /***
     * 同步房屋住户数据
     * @return
     */
    Boolean syncHouseHoldInfo();

    /***
     * mock
     * @param houseList
     * @return
     */
    Boolean mockHouseInfo(List<MockHouse> houseList);

}
