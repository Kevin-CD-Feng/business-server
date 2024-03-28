package com.xtxk.cn.third.controller;

import com.xtxk.cn.third.common.CommonResponse;
import com.xtxk.cn.third.service.AccessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-04 17:24
 */
@RestController
@RequestMapping("/accessControl")
@Api(tags = {"门禁闸机相关"})
public class AccessController {

    @Autowired
    private AccessService accessService;

    @GetMapping("/sync/pullAccessControlInfo")
    @ApiOperation("拉取门禁闸机数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "0", paramType = "query", value = "页码(从0开始)", required = true),
            @ApiImplicitParam(name = "size", defaultValue = "200", value = "条数", paramType = "query", required = true)
    })
    public CommonResponse pullAccessControlInfo(@RequestParam Integer page, @RequestParam Integer size) {
        return CommonResponse.success("拉取成功", accessService.pullAccessControlInfo(page, size));
    }


    @GetMapping("/syncAccessControlInfo")
    @ApiOperation("手动同步门禁闸机数据")
    public CommonResponse syncAccessControlInfo() {
        return CommonResponse.success("同步成功", accessService.syncAccessControlInfo());
    }

}
