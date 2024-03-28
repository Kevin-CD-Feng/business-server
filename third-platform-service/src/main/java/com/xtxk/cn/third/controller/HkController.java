package com.xtxk.cn.third.controller;

import com.xtxk.cn.third.dto.hk.EventObjectsThrownDetection;
import com.xtxk.cn.third.common.CommonResponse;
import com.xtxk.cn.third.service.HkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-21 21:01
 */
@RestController
@Api(tags = {"海康相关"})
public class HkController {

    @Autowired
    private HkService hkService;

    @PostMapping("/eventRcv")
    @ApiOperation("海康高空抛物回调地址")
    public CommonResponse eventRcv(@RequestBody EventObjectsThrownDetection detection){
        return CommonResponse.success(hkService.eventRcv(detection));
    }


    @ApiOperation("开启海康高空抛物")
    @GetMapping("/startSub")
    public CommonResponse startSub(){
        return CommonResponse.success(hkService.startSub());
    }

    @GetMapping("/subView")
    @ApiOperation("查询高空抛物订阅信息")
    public CommonResponse subView(){
        return CommonResponse.success(hkService.subView());
    }

    @ApiOperation("停止海康高空抛物")
    @GetMapping("/stopSub")
    public CommonResponse stopSub(){
        return CommonResponse.success(hkService.stopSub());
    }




}
