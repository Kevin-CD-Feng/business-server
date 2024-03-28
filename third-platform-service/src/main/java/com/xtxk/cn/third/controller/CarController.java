package com.xtxk.cn.third.controller;

import com.xtxk.cn.third.dto.car.MockCarInfo;
import com.xtxk.cn.third.common.CommonResponse;
import com.xtxk.cn.third.service.CarService;
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
@RequestMapping("/car")
@Api(tags = {"小区车辆相关"})
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/pullCarInfo")
    @ApiOperation("拉取区域湖小区车辆数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "0", paramType = "query", value = "页码(从0开始)", required = true),
            @ApiImplicitParam(name = "size", defaultValue = "200", value = "条数", paramType = "query", required = true)
    })
    public CommonResponse pullCarInfo(@RequestParam Integer page, @RequestParam Integer size) {
        return CommonResponse.success("拉取成功", carService.pullCarInfo(page, size));
    }


    @GetMapping("/syncCarInfo")
    @ApiOperation("同步区域湖小区车辆数据")
    public CommonResponse syncCarInfo() {
        return CommonResponse.success("拉取成功", carService.syncCarInfo());
    }

    @PostMapping("/mockInfo")
    @ApiOperation("智慧小区车辆mock数据接口")
    public CommonResponse mockCarInfo(@RequestBody List<MockCarInfo> mockInfo) {
        return CommonResponse.success("操作成功", carService.mockCarInfo(mockInfo));
    }


}
