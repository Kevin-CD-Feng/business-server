package com.xtxk.cn.controller.user;

import cn.hutool.json.JSONUtil;
import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.dto.user.*;
import com.xtxk.cn.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"用户管理模块"}, description = "用户信息表管理")
@RestController
@RequestMapping("/user")
@Validated
@AspectLogger
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "权限更新")
    @PostMapping(value = "/updatePermission")
    public CommonResponse updatePermission(@Valid @RequestBody UpdatePermissionReqDto updatePermissionReqDto){
        userService.updatePermission(updatePermissionReqDto);
        return CommonResponse.success("权限更新成功");
    }

    @ApiOperation(value = "查询权限")
    @PostMapping(value = "/queryPermission")
    public CommonResponse<UserPermissionRespDto> queryPermission(@Valid @RequestBody UserPermissionReqDto userPermissionReqDto){
        return CommonResponse.success(userService.queryPermission(userPermissionReqDto));
    }

}
