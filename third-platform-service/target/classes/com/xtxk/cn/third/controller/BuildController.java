package com.xtxk.cn.third.controller;

import com.xtxk.cn.third.dto.build.MockBuild;
import com.xtxk.cn.third.common.CommonResponse;
import com.xtxk.cn.third.service.BuildService;
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
 * @date 2023-08-22 10:29
 */
@RestController
@RequestMapping("/build")
@Api(tags = {"小区楼宇相关"})
public class BuildController {

    @Autowired
    private BuildService buildService;

    @GetMapping("/sync/pullBuildInfo")
    @ApiOperation("拉取区域湖小区楼栋数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "0", paramType = "query", value = "页码(从0开始)", required = true),
            @ApiImplicitParam(name = "size", defaultValue = "200", value = "条数", paramType = "query", required = true)
    })
    public CommonResponse pullBuildInfo(@RequestParam Integer page, @RequestParam Integer size) {
        return CommonResponse.success("拉取成功", buildService.pullBuildInfo(page, size));
    }

    @GetMapping("/syncBuildInfo")
    @ApiOperation("同步区域湖小区楼栋数据")
    public CommonResponse syncUserInfo() {
        return CommonResponse.success("拉取成功", buildService.syncBuild());
    }

    @PostMapping("/mockInfo")
    @ApiOperation("智慧小区楼栋mock数据接口")
    public CommonResponse mockUserInfo(@RequestBody List<MockBuild> buildList) {
        return CommonResponse.success("拉取成功", buildService.mockInfo(buildList));
    }

}
