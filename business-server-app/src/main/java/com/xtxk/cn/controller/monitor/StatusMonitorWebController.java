package com.xtxk.cn.controller.monitor;

import cn.hutool.json.JSONUtil;
import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.dto.statusMonitorWebConvert.UserPageReqDto;
import com.xtxk.cn.dto.statusMonitorWebConvert.UserListRspDto;
import com.xtxk.cn.dto.statusMonitorWebConvert.UserPageRspDto;
import com.xtxk.cn.service.statusmonitorweb.StatusMonitorWebConvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Api(tags = {"openvone运营监控平台交互模块"}, description = "openvone运营监控平台交互模块")
@RestController
@RequestMapping("/openvone")
@AspectLogger
public class StatusMonitorWebController {
    @Autowired
    private StatusMonitorWebConvertService statusMonitorWebConvertService;

    @PostMapping("/getAllUser")
    @ApiOperation(value = "获取所有临时用户(小区用户)  下拉获取用户")
    public CommonResponse<UserListRspDto> getAllUser() {
        UserListRspDto userListRspDto = statusMonitorWebConvertService.userList();
        return CommonResponse.success(userListRspDto);
    }

    @PostMapping("/getUserPage")
    @ApiOperation(value = "获取临时用户列表(小区用户) 分页用户数据")
    public CommonResponse<UserPageRspDto> getUserPage(@Valid @RequestBody UserPageReqDto userPageReqDto) {
        Integer accountType = userPageReqDto.getAccountType();
        if(accountType == 1){
            return CommonResponse.success(new UserPageRspDto());
        }else {
            UserPageRspDto userPageRspDto = statusMonitorWebConvertService.userPage(userPageReqDto);
            return CommonResponse.success(userPageRspDto);
        }
    }
}
