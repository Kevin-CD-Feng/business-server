package com.xtxk.cn.controller.geo;

import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.dto.geogra.GeograObjDto;
import com.xtxk.cn.dto.geogra.GeograObjResult;
import com.xtxk.cn.service.geo.GeograService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"地理信息管理模块"}, description = "地理信息管理")
@RestController
@RequestMapping("/geogra")
@AspectLogger
public class GeograController {
    @Autowired
    private GeograService geograService;

    @GetMapping
    @ApiOperation(value = "获取地理信息结果")
    public CommonResponse<GeograObjResult> getGeograResult(@RequestParam(value = "type", required = false, defaultValue = "1,2,3") @ApiParam(value = "类型") String type) {
        GeograObjResult result = new GeograObjResult();
        List<GeograObjDto> list = geograService.getGeograObjResult(type);
        result.setResources(list);
        return CommonResponse.success(result);
    }

}
