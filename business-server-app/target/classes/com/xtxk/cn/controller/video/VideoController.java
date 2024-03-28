package com.xtxk.cn.controller.video;

import cn.hutool.json.JSONUtil;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.dto.video.VideoParam;
import com.xtxk.cn.dto.video.VideoReq;
import com.xtxk.cn.service.video.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-07 10:12
 */
@RestController
@RequestMapping("/video")
@Api(tags = {"录像相关"})
public class VideoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private VideoService videoService;


    @ApiOperation(value = "开启录像")
    @PostMapping(value = "/startVideo")
    public CommonResponse startVideo(@RequestBody VideoParam param) {
        LOGGER.info("请求录像服务:startVideo,{}", JSONUtil.toJsonStr(param));
        return CommonResponse.success(videoService.startVideo(param.getResourceId(),param.getResourceName(), param.getResourceSipId()));
    }


    @ApiOperation(value = "录像状态回馈")
    @PostMapping(value = "/statusRepay")
    public CommonResponse statusRepay(@Valid @RequestBody VideoReq videoReq) {
        LOGGER.info("请求录像服务:statusRepay,{}", JSONUtil.toJsonStr(videoReq));
        return CommonResponse.success(videoService.statusRepay(videoReq));
    }

    @ApiOperation(value = "录像设备停止录像")
    @PostMapping(value = "/stopVideo")
    public CommonResponse stopVideo(@RequestBody VideoParam req) {
        LOGGER.info("请求录像停止服务:stopVideo,{}", req.getResourceId());
        return CommonResponse.success(videoService.stopVideo(req.getResourceId()));
    }

}
