package com.xtxk.cn.service.impl.user;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.xtxk.cn.dto.user.UpdatePermissionReqDto;
import com.xtxk.cn.dto.user.UserPermissionReqDto;
import com.xtxk.cn.dto.user.UserPermissionRespDto;
import com.xtxk.cn.entity.UserPermission;
import com.xtxk.cn.mapper.UserPermissionMapper;
import com.xtxk.cn.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserPermissionMapper userPermissionMapper;

    @Override
    public void updatePermission(UpdatePermissionReqDto updatePermissionReqDto) {

        /** userid对应权限不存在就做更新操作 */
        UserPermission userPermission1 = userPermissionMapper.queryByUserId(updatePermissionReqDto.getId());
        String[] permissionType = updatePermissionReqDto.getPermissionType();
        UserPermission userPermission = new UserPermission();
        userPermission.setPermission(JSONUtil.toJsonStr(permissionType));
        userPermission.setUserId(updatePermissionReqDto.getId());
        userPermission.setAccountType(updatePermissionReqDto.getAccountType());
        if(ObjectUtil.isNotEmpty(userPermission1)){
            userPermissionMapper.updatePermission(userPermission);
        }else{
            userPermissionMapper.insert(userPermission);
        }


    }

    @Override
    public UserPermissionRespDto queryPermission(UserPermissionReqDto userPermissionReqDto) {
        UserPermissionRespDto userPermissionRespDto = new UserPermissionRespDto();
        String permission = userPermissionMapper.queryPermission(userPermissionReqDto.getUserId());
        if(ObjectUtil.isNotEmpty(permission)) {
            Object str = JSONUtil.parseArray(permission).toArray(String[].class);
            userPermissionRespDto.setPermission((String[])str);
        }else{
            String[] empty= {};
            userPermissionRespDto.setPermission(empty);
        }
        return userPermissionRespDto;
    }
}
