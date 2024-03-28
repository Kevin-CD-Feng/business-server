package com.xtxk.cn.service.impl.statusmonitorweb;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.xtxk.cn.controller.monitor.StatusMonitorWebController;
import com.xtxk.cn.dto.statusMonitorWeb.QueryUsersByConditionsReqDto;
import com.xtxk.cn.dto.statusMonitorWeb.QueryUsersByConditionsRspDto;
import com.xtxk.cn.dto.statusMonitorWebConvert.UserPageReqDto;
import com.xtxk.cn.dto.statusMonitorWebConvert.UserListRspDto;
import com.xtxk.cn.dto.statusMonitorWebConvert.UserPageRspDto;
import com.xtxk.cn.entity.UserPermission;
import com.xtxk.cn.mapper.UserPermissionMapper;
import com.xtxk.cn.service.statusmonitorweb.StatusMonitorWebConvertService;
import com.xtxk.cn.service.statusmonitorweb.StatusMonitorWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wululu(wululu1 @ xingtu.com)
 * @Description 获取用户数据
 * @Date create in 2022/11/7 16:03
 */
@Service
public class StatusMonitorWebConvertServiceImpl implements StatusMonitorWebConvertService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusMonitorWebController.class);

    @Autowired
    private StatusMonitorWebService statusMonitorWebService;

    @Autowired
    private UserPermissionMapper userPermissionMapper;

    private QueryUsersByConditionsRspDto userPage(Integer beginIndex, Integer count, String resouceSign) {
        //分页数据
        QueryUsersByConditionsReqDto.Param param = new QueryUsersByConditionsReqDto.Param(beginIndex, count, resouceSign);
        QueryUsersByConditionsReqDto queryUsersByConditionsReqDto = new QueryUsersByConditionsReqDto(param);
        return statusMonitorWebService.queryUsersByConditions(queryUsersByConditionsReqDto);
    }

    @Override
    public UserListRspDto userList() {
        Integer beginIndex = 0;
        Integer count = 1000;
        QueryUsersByConditionsRspDto queryUsersByConditionsRspDto = userPage(beginIndex, count, "");
        List<UserListRspDto.User> users = Arrays.stream(queryUsersByConditionsRspDto.getReturnValue().getList()).map(temp -> {
            UserListRspDto.User user = new UserListRspDto.User();
            user.setUserAccount(temp.getUserAccount());
            user.setId(temp.getUserID());
            user.setName(temp.getUserName());
            return user;
        }).collect(Collectors.toList());
        UserListRspDto userListRspDto = new UserListRspDto();
        userListRspDto.setUsers(users);
        return userListRspDto;
    }

    @Override
    public UserPageRspDto userPage(UserPageReqDto userPageReqDto) {
        Integer beginIndex = (userPageReqDto.getPageNo() - 1) * userPageReqDto.getPageSize();
        Integer count = userPageReqDto.getPageSize();
        QueryUsersByConditionsRspDto queryUsersByConditionsRspDto = userPage(beginIndex, count, userPageReqDto.getKey());
        Integer totalCount = queryUsersByConditionsRspDto.getReturnValue().getTotalCount();
        List<UserPageRspDto.User> users = Arrays.stream(queryUsersByConditionsRspDto.getReturnValue().getList()).map(temp -> {
            UserPageRspDto.User user = new UserPageRspDto.User();
            user.setAccountName(temp.getUserAccount());
            user.setId(temp.getUserID());
            user.setUsername(temp.getUserName());
            user.setCreateTime(temp.getValidBeginDate());
            user.setValidEndDate(temp.getValidEndDate());
            return user;
        }).collect(Collectors.toList());
        users.sort(new Comparator<UserPageRspDto.User>() {
            @Override
            public int compare(UserPageRspDto.User o1, UserPageRspDto.User o2) {
                try {
                    Collator in = Collator.getInstance(Locale.CHINA);
//                    if( in.compare(o1.getCreateTime(), o2.getCreateTime()) < 0 )
//                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date d1 = format.parse(o1.getCreateTime());
//                    Date d2 = format.parse(o2.getCreateTime());
                    return in.compare(o1.getId(), o2.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

        //todo  数据库比对 获取权限
        users.forEach(item -> {
            /** 根据userId查询权限 */
            String userID = item.getId();
            UserPermission userPermission = userPermissionMapper.queryByUserId(userID);
            if (ObjectUtil.isNotEmpty(userPermission)) {
                String permission1 = userPermission.getPermission();
                if (ObjectUtil.isNotEmpty(permission1)) {
                    Object permission = JSONUtil.parseArray(userPermission.getPermission()).toArray(String[].class);
                    item.setPermission((String[]) permission);
                }

            } else {
                String[] permission = {};
                item.setPermission(permission);
            }
        });

        UserPageRspDto userPageRspDto = new UserPageRspDto();
        userPageRspDto.setCount(totalCount);
        userPageRspDto.setPageCount(totalCount % userPageReqDto.getPageSize() != 0 ? totalCount / userPageReqDto.getPageSize() + 1 : totalCount / userPageReqDto.getPageSize());
        userPageRspDto.setUsers(users);
        return userPageRspDto;
    }
}
