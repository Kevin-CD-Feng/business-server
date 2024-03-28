package com.xtxk.cn.controller.statistic;

import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.dto.statistic.event.EventParam;
import com.xtxk.cn.dto.statistic.event.ExportParam;
import com.xtxk.cn.service.statistic.EventStatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpServletResponse;

/***
 * @description 事件处理统计
 * @author liulei
 * @date 2023-09-07 15:11
 */
@RestController
@RequestMapping("/eventStatistic")
@Api(tags = "事件处理统计")
public class EventStatisticController {

    @Autowired
    private EventStatisticService eventStatisticService;

    @GetMapping("/status")
    @ApiOperation("事件处理状态统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eventSource", value = "事件来源 0 智能上报 1 人工上报"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "结束时间"),
            @ApiImplicitParam(name = "type", value = "0(周) 1(月) 2(年)")
    })
    public CommonResponse status(@ApiIgnore EventParam param) {
        return CommonResponse.success("查询成功", eventStatisticService.status(param));
    }

    @GetMapping("/type")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eventSource", value = "事件来源 0 智能上报 1 人工上报"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "结束时间"),
            @ApiImplicitParam(name = "type", value = "0(周) 1(月) 2(年)")
    })
    @ApiOperation("事件处理类型统计")
    public CommonResponse type(@ApiIgnore EventParam eventParam) {
        return CommonResponse.success("查询成功", eventStatisticService.type(eventParam));
    }

    @GetMapping("/handle")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eventSource", value = "事件来源 0 智能上报 1 人工上报"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "结束时间"),
            @ApiImplicitParam(name = "type", value = "0(周) 1(月) 2(年)")
    })
    @ApiOperation("告警处理统计")
    public CommonResponse handle(@ApiIgnore EventParam eventParam) {
        return CommonResponse.success("查询成功", eventStatisticService.handle(eventParam));
    }

    @GetMapping("/table")
    @ApiOperation("事件列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eventSource", value = "事件来源 0 智能上报 1 人工上报"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "结束时间"),
            @ApiImplicitParam(name = "type", value = "0(周) 1(月) 2(年)")
    })
    public CommonResponse table(@ApiIgnore EventParam eventParam) {
        return CommonResponse.success("查询成功", eventStatisticService.table(eventParam));
    }

    @PostMapping("/export")
    @ApiOperation(value = "导出")
    public void export(@RequestBody ExportParam exportParam, HttpServletResponse response) throws Exception {
        eventStatisticService.export(exportParam,response);
    }


    @GetMapping("/selectEventItem")
    @ApiOperation(value = "根据告警类型查询告警事件")
    public CommonResponse selectEventItem(@RequestParam String eventCode) {
        return CommonResponse.success("查询成功", eventStatisticService.selectEventItem(eventCode));
    }



}
