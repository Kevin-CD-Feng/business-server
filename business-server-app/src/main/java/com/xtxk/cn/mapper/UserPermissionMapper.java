package com.xtxk.cn.mapper;

import com.xtxk.cn.entity.UserPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserPermissionMapper {

    UserPermission queryByUserId(@Param("userId") String userId);

    void updatePermission(UserPermission userPermission);

    void insert(UserPermission userPermission);

    String queryPermission(@Param("userId") String userId);
}
