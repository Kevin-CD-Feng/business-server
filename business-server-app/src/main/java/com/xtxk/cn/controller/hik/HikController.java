package com.xtxk.cn.controller.hik;

import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.dto.hik.*;
import com.xtxk.cn.service.hk.HKService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = {"海康平台模块"}, description = "海康平台对接")
@RestController
@RequestMapping("/hkPlatform")
@AspectLogger
public class HikController {

    @Autowired
    private HKService hkService;

    @ApiOperation(value = "获取海康监控列表")
    @PostMapping("/getHKResources")
    public CommonResponse<HKCameraResourceResp> getHKResources(@Valid @RequestBody HKResourcesReq request) {
        return CommonResponse.success(hkService.getHKResources(request));
    }

    @ApiOperation(value = "获取海康监控预览路径")
    @PostMapping("/getHKCameraPreviewUrl")
    public CommonResponse<HKCameraResp> getHKCameraPreviewUrl(@Valid @RequestBody HKCameraPreviewUrlReq request) {
        return CommonResponse.success(hkService.getHKCameraPreviewUrl(request));
    }

    @ApiOperation(value = "获取海康监控录像回放路径")
    @PostMapping("/getHKCameraPlaybackUrl")
    public CommonResponse<HKCameraResp> getHKCameraPlaybackUrl(@Valid @RequestBody HKCameraPlaybackUrlReq request) {
        return CommonResponse.success(hkService.getHKCameraPlaybackUrl(request));
    }
}
