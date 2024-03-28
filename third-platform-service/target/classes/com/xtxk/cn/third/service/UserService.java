package com.xtxk.cn.third.service;

import com.xtxk.cn.third.dto.user.MockUserInfo;

import java.util.List;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-2210:36
 */
public interface UserService {

    /***
     * 拉取小区人员数据
     * @param page
     * @param size
     * @return
     */
     Boolean pullUserInfo(Integer page, Integer size);

    /***
     * 同步小区人员数据
     * @return
     */
     Boolean syncUserInfo();

    /***
     * mock
     * @param mockUserInfo
     * @return
     */
     Boolean mockUserInfo(List<MockUserInfo> mockUserInfo);
}
