package com.xtxk.cn.controller.patrolstrategy;

import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.dto.patrolstrategy.Params;
import com.xtxk.cn.dto.patrolstrategy.PatrolStrategyDTO;
import com.xtxk.cn.service.patrolstrategy.PatrolStrategyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/patrolStrategy")
@Api(tags = {"轮巡组配置"})
@AspectLogger
public class PatrolStrategyController {

    @Autowired
    private PatrolStrategyService patrolStrategyService;

    @PostMapping("/add")
    @ApiOperation("轮巡方案创建")
    public CommonResponse add(@Valid @RequestBody PatrolStrategyDTO patrolStrategyDto) {
        return CommonResponse.success("创建成功", patrolStrategyService.add(patrolStrategyDto));
    }

    @PostMapping("/update")
    @ApiOperation("轮巡方案编辑")
    public CommonResponse update(@Valid @RequestBody PatrolStrategyDTO patrolStrategyDto) {
        return CommonResponse.success("编辑成功", patrolStrategyService.update(patrolStrategyDto));
    }

    @GetMapping("/delete")
    @ApiOperation("轮巡方案删除")
    public CommonResponse delete(@RequestParam String strategyId) {
        return CommonResponse.success("编辑成功", patrolStrategyService.delete(strategyId));
    }


    @PostMapping("/queryList")
    @ApiOperation("轮巡方案查询")
    public CommonResponse queryList(@RequestBody Params params){
        return CommonResponse.success("查询成功",patrolStrategyService.queryList(params));
    }
}
