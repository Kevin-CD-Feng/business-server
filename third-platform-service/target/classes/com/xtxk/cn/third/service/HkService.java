package com.xtxk.cn.third.service;

import com.xtxk.cn.third.dto.hk.EventObjectsThrownDetection;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-2210:38
 */
public interface HkService {

    /***
     * 海康高空抛物回调
     * @param detection
     * @return
     */
    boolean eventRcv(EventObjectsThrownDetection detection);

    /***
     *
     * @return
     */
    boolean startSub();

    /***
     * 查询高空抛物订阅信息
     * @return
     */
    String subView();

    /***
     * 停止订阅高空抛物订阅
     * @return
     */
    String stopSub();

}
