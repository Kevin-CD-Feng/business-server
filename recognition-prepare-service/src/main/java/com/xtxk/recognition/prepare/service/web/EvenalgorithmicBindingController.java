package com.xtxk.recognition.prepare.service.web;

import com.xtxk.recognition.prepare.service.common.CommonResponse;
import com.xtxk.recognition.prepare.service.component.annotation.AspectLogger;
import com.xtxk.recognition.prepare.service.dto.EvenalgorithmicBinding.*;
import com.xtxk.recognition.prepare.service.svc.EvenalgorithmicBindingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = {"事件算法绑定表模块"}, description = "事件算法绑定表管理")
@RestController
@RequestMapping("/evenalgorithmicBinding")
@AspectLogger
public class EvenalgorithmicBindingController {
    @Autowired
    private EvenalgorithmicBindingService evenalgorithmicBindingService;

    @ApiOperation(value = "事件名称列表")
    @PostMapping(value = "/eventList")
    public CommonResponse eventList(){
        return CommonResponse.success(evenalgorithmicBindingService.eventList());
    }

    @ApiOperation(value = "根据eventCode查询算法")
    @PostMapping(value = "/queryAlgorithmic")
    public CommonResponse queryAlgorithmic(@Valid @RequestBody QueryAlgorithmicInfoReqDto queryAlgorithmicInfoReqDto){
        return CommonResponse.success(evenalgorithmicBindingService.queryAlgorithmic(queryAlgorithmicInfoReqDto.getEventCode()));
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public CommonResponse add(@Valid @RequestBody AddAlgorithmicBindingReqDto addAlgorithmicBindingReqDto) {
        evenalgorithmicBindingService.add(addAlgorithmicBindingReqDto);
        return CommonResponse.success("新增成功");
    }

    @ApiOperation(value = "事件算法绑定列表")
    @PostMapping(value = "/list")
    public CommonResponse<PageAlgorithmicBindingRespDto> list(@Valid @RequestBody PageAlgorithmicBindingReqDto pageAlgorithmicBindingReqDto){
        PageAlgorithmicBindingRespDto pageAlgorithmicBindingRespDto = evenalgorithmicBindingService.pageList(pageAlgorithmicBindingReqDto);
        return CommonResponse.success(pageAlgorithmicBindingRespDto);
    }

    @ApiOperation(value = "查询根据id详情")
    @PostMapping(value = "/detail")
    public CommonResponse<QueryAlgorithmicBindingDetailRespDto> detail(@Valid @RequestBody QueryAlgorithmicBindingDetailReqDto queryAlgorithmicBindingDetailReqDto) {
        QueryAlgorithmicBindingDetailRespDto queryAlgorithmicBindingDetailRespDto = evenalgorithmicBindingService.detail(queryAlgorithmicBindingDetailReqDto.getId());
        return CommonResponse.success(queryAlgorithmicBindingDetailRespDto);
    }

    @ApiOperation(value = "更新")
    @PostMapping(value = "/update")
    public CommonResponse update(@Valid @RequestBody UpdateAlgorithmicBindingReqDto updateAlgorithmicBindingReqDto) {
        evenalgorithmicBindingService.update(updateAlgorithmicBindingReqDto);
        return CommonResponse.success("更新成功");
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public CommonResponse delete(@Valid @RequestBody DeleteAlgorithmicBindingReqDto deleteAlgorithmicBindingReqDto) {
        evenalgorithmicBindingService.delete(deleteAlgorithmicBindingReqDto.getIds());
        return CommonResponse.success("删除成功");
    }
}