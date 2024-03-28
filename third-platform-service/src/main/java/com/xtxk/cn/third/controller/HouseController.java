package com.xtxk.cn.third.controller;

import com.xtxk.cn.third.common.CommonResponse;
import com.xtxk.cn.third.dto.house.MockHouse;
import com.xtxk.cn.third.service.HouseService;
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
 * @date 2023-08-22 10:30
 */
@RestController
@RequestMapping("/house")
@Api(tags = {"小区出租屋相关"})
public class HouseController {

    @Autowired
    private HouseService houseService;

    @GetMapping("/sync/pullHouseInfo")
    @ApiOperation("拉取区域湖小区房屋数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "0", paramType = "query", value = "页码(从0开始)", required = true),
            @ApiImplicitParam(name = "size", defaultValue = "200", value = "条数", paramType = "query", required = true)
    })
    public CommonResponse pullHouseInfo(@RequestParam Integer page, @RequestParam Integer size) {
        return CommonResponse.success("拉取成功", houseService.pullHouseInfo(page, size));
    }

    @GetMapping("/syncHouseInfo")
    @ApiOperation("同步区域湖小区房屋数据")
    public CommonResponse syncHouseInfo() {
        return CommonResponse.success("拉取成功", houseService.syncHouse());
    }


    @GetMapping("/sync/pullHouseHoldInfo")
    @ApiOperation("拉取区域湖小区房屋住户数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "0", paramType = "query", value = "页码(从0开始)", required = true),
            @ApiImplicitParam(name = "size", defaultValue = "200", value = "条数", paramType = "query", required = true)
    })
    public CommonResponse pullHouseHoldInfo(@RequestParam Integer page, @RequestParam Integer size) {
        return CommonResponse.success("拉取成功", houseService.pullHouseHoldInfo(page, size));
    }

    @GetMapping("/syncHouseHoldInfo")
    @ApiOperation("同步区域湖小区房屋住户数据")
    public CommonResponse syncHouseHoldInfo() {
        return CommonResponse.success("拉取成功", houseService.syncHouseHoldInfo());
    }

    @PostMapping("/mockInfo")
    @ApiOperation("智慧小区房屋mock数据接口")
    public CommonResponse mockHouseInfo(@RequestBody List<MockHouse> houseList) {
        return CommonResponse.success("拉取成功", houseService.mockHouseInfo(houseList));
    }
}
