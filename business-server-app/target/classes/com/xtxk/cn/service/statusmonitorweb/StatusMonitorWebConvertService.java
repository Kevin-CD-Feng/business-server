package com.xtxk.cn.service.statusmonitorweb;

import com.xtxk.cn.dto.statusMonitorWebConvert.UserListRspDto;
import com.xtxk.cn.dto.statusMonitorWebConvert.UserPageReqDto;
import com.xtxk.cn.dto.statusMonitorWebConvert.UserPageRspDto;

/**
 * @author wululu(wululu1 @ xingtu.com)
 * @Description 获取用户数据
 * @Date create in 2022/11/7 15:59
 */
public interface StatusMonitorWebConvertService {

    /**
     * 查询指定组织机构下所有用户
     *
     * @return
     */
    UserListRspDto userList();

    /**
     * 查询指定组织机构下分页用户
     *
     * @param userPageReqDto
     * @return
     */
    UserPageRspDto userPage(UserPageReqDto userPageReqDto);
}