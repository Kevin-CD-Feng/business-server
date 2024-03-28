package com.xtxk.recognition.prepare.service.web;

import com.xtxk.recognition.prepare.service.common.CommonResponse;
import com.xtxk.recognition.prepare.service.component.annotation.AspectLogger;
import com.xtxk.recognition.prepare.service.dto.algorithmicModel.*;
import com.xtxk.recognition.prepare.service.svc.AlgorithmicModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = {"算法模型管理表模块"})
@RestController
@RequestMapping("/algorithmicModel")
@AspectLogger
public class AlgorithmicModelController {
    @Autowired
    private AlgorithmicModelService algorithmicModelService;

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public CommonResponse add(@Valid @RequestBody AddAlgorithmicModelReqDto addAlgorithmicModelReqDto) {
        algorithmicModelService.add(addAlgorithmicModelReqDto);
        return CommonResponse.success("新增成功");
    }

    @ApiOperation(value = "查询根据id详情")
    @PostMapping(value = "/detail")
    public CommonResponse<DetailAlgorithmicModelRespDto> detail(@Valid @RequestBody DetailAlgorithmicModelReqDto detailAlgorithmicModelReqDto) {
        DetailAlgorithmicModelRespDto detailAlgorithmicModelRespDto = algorithmicModelService.detail(detailAlgorithmicModelReqDto.getId());
        return CommonResponse.success(detailAlgorithmicModelRespDto);
    }

    @ApiOperation(value = "更新")
    @PostMapping(value = "/update")
    public CommonResponse update(@Valid @RequestBody UpdateAlgorithmicModelReqDto updateAlgorithmicModelReqDto) {
        algorithmicModelService.update(updateAlgorithmicModelReqDto);
        return CommonResponse.success("更新成功");
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public CommonResponse delete(@Valid @RequestBody DeleteAlgorithmicModelReqDto deleteAlgorithmicModelReqDto) {
        algorithmicModelService.delete(deleteAlgorithmicModelReqDto.getIds());
        return CommonResponse.success("删除成功");
    }

    @ApiOperation(value = "算法模型列表")
    @PostMapping(value = "/list")
    public CommonResponse<PageAlgorithmicModelRespDto> list(@Valid @RequestBody PageAlgorithmicModelReqDto pageAlgorithmicModelReqDto){
        PageAlgorithmicModelRespDto pageAlgorithmicModelRespDto = algorithmicModelService.pageList(pageAlgorithmicModelReqDto);
        return CommonResponse.success(pageAlgorithmicModelRespDto);
    }

    @ApiOperation(value = "算法名称列表")
    @PostMapping(value = "/nameList")
    public CommonResponse nameList(){
        return CommonResponse.success(algorithmicModelService.nameList());
    }

    @ApiOperation(value = "算法服务地址校验")
    @PostMapping(value = "/checkAddress")
    public CommonResponse checkAddress(@Valid @RequestBody CheckAddressReqDto checkAddressReqDto){
        return null;
    }

    @ApiOperation(value = "算法列表")
    @PostMapping(value = "/algorithmicList")
    public CommonResponse<AlgorithmicListDto> algorithmicList(){
        return CommonResponse.success(algorithmicModelService.algorithmicList());
    }
}