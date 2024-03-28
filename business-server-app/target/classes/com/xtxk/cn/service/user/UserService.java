package com.xtxk.cn.service.user;

import com.xtxk.cn.dto.user.UpdatePermissionReqDto;
import com.xtxk.cn.dto.user.UserPermissionReqDto;
import com.xtxk.cn.dto.user.UserPermissionRespDto;

import java.util.List;

public interface UserService {

    /**
     * 根据userId和账户类型查找信息
     * @param updatePermissionReqDto
     */
    void updatePermission(UpdatePermissionReqDto updatePermissionReqDto);

    /**
     * 根据userId查询权限
     * @param userPermissionReqDto
     * @return
     */
    UserPermissionRespDto queryPermission(UserPermissionReqDto userPermissionReqDto);
}
