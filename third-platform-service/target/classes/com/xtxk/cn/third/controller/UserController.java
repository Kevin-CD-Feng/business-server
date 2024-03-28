package com.xtxk.cn.third.controller;

import com.xtxk.cn.third.dto.user.MockUserInfo;
import com.xtxk.cn.third.service.UserService;
import com.xtxk.cn.third.common.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-22 10:28
 */
@RestController
@RequestMapping("/user")
@Api(tags = {"小区人员相关"})
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/sync/pullUserInfo")
    @ApiOperation("拉取区域湖小区人员数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "0", paramType = "query", value = "页码(从0开始)", required = true),
            @ApiImplicitParam(name = "size", defaultValue = "200", value = "条数", paramType = "query", required = true)
    })
    public CommonResponse pullUserInfo(@RequestParam Integer page, @RequestParam Integer size) {
        return CommonResponse.success("拉取成功", userService.pullUserInfo(page, size));
    }

    @GetMapping("/syncUserInfo")
    @ApiOperation("同步区域湖小区人员数据")
    public CommonResponse syncUserInfo() {
        return CommonResponse.success("拉取成功", userService.syncUserInfo());
    }

    @PostMapping("/mockInfo")
    @ApiOperation("智慧小区人员mock数据接口")
    public CommonResponse mockUserInfo(@RequestBody List<MockUserInfo> mockUserInfo) {
        return CommonResponse.success("操作成功", userService.mockUserInfo(mockUserInfo));
    }



}
