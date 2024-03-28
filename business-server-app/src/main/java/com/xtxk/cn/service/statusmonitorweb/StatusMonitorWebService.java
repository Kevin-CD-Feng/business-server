package com.xtxk.cn.service.statusmonitorweb;

import com.xtxk.cn.dto.statusMonitorWeb.*;

/**
 * @author wululu(wululu1 @ xingtu.com)
 * @Description 与运营监控平台交互
 * @Date create in 2022/11/7 9:51
 */
public interface StatusMonitorWebService {
    /**
     * 查询部门下用户
     *
     * @param queryUsersByConditionsReqDto
     * @return
     */
    QueryUsersByConditionsRspDto queryUsersByConditions(QueryUsersByConditionsReqDto queryUsersByConditionsReqDto);
}