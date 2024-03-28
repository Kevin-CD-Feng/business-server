package com.xtxk.cn.controller.geo;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageInfo;
import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.common.PageRspDto;
import com.xtxk.cn.dto.geogra.AddGateMachineReqDto;
import com.xtxk.cn.dto.geogra.UpdateGateMachineReqDto;
import com.xtxk.cn.entity.GateMachinePO;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.service.gatemachine.GateMachineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


@Api(tags = {"地理信息管理模块-门禁闸机"}, description = "门禁闸机")
@RestController
@RequestMapping("/gateMachine")
@AspectLogger
public class GateMachineController {
    @Autowired
    private GateMachineService gateMachineService;

    @GetMapping("/page")
    @ApiOperation(value = "分页查询门禁闸机")
    public CommonResponse<PageRspDto<GateMachinePO>> getGateMachineList(@RequestParam(value = "keyWord", required = false) @ApiParam(value = "关键字") String keyWord,
                                                                        @RequestParam(value = "pageNum", required = false, defaultValue = "1") @ApiParam(value = "当前页数") Integer pageNum,
                                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "每页条数") Integer pageSize) {
        PageInfo<GateMachinePO> pageInfo = gateMachineService.getGateMachineList(keyWord, pageNum, pageSize);
        return CommonResponse.success(new PageRspDto<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList()));
    }

    @ApiOperation(value = "新增门禁闸机")
    @PostMapping
    public CommonResponse addGateMachine(@Valid @RequestBody AddGateMachineReqDto addGateMachineReqDto) {
        gateMachineService.addGateMachine(addGateMachineReqDto);
        return CommonResponse.success("新增成功");
    }

    @ApiOperation(value = "修改门禁闸机信息")
    @PutMapping("/{gateMachineId}")
    public CommonResponse updateGateMachine(@PathVariable("gateMachineId") @ApiParam(value = "门禁闸机id", required = true) Integer gateMachineId, @Valid @RequestBody UpdateGateMachineReqDto updateGateMachineReqDto) {
       if (null == gateMachineId) {
            throw new ServiceException(ErrorCode.GATEMACHINE_ID_NULL);
        }
        gateMachineService.updateGateMachine(gateMachineId, updateGateMachineReqDto);
        return CommonResponse.success("修改成功");
    }

    @ApiOperation(value = "删除门禁闸机信息")
    @DeleteMapping("/{gateMachineId}")
    public CommonResponse deleteGateMachine(@PathVariable("gateMachineId") @ApiParam(value = "门禁闸机id", required = true) Integer gateMachineId) {
       if (null == gateMachineId) {
            throw new ServiceException(ErrorCode.GATEMACHINE_ID_NULL);
        }
        gateMachineService.deleteGateMachine(gateMachineId);
        return CommonResponse.success("删除成功");
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出门禁闸机信息")
    public void exportGateMachine(@RequestParam(value = "keyWord", required = false) @ApiParam(value = "关键字") String keyWord, HttpServletResponse response) {
       List<GateMachinePO> list = gateMachineService.getExportGateMachine(keyWord);
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(System.currentTimeMillis()
                    + "门禁闸机信息", "UTF-8") + ".xlsx");
            EasyExcel.write(response.getOutputStream())
                    .head(GateMachinePO.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("门禁闸机信息")
                    .doWrite(list);
        } catch (IOException e) {
            throw new ServiceException(ErrorCode.ERROR, "导出报表异常");
        }
    }

    @GetMapping
    @ApiOperation(value = "查询单个门禁闸机")
    public CommonResponse<GateMachinePO> getGateMachine(@RequestParam(value = "gateMachineId") @ApiParam(value = "门禁闸机id",required = true) Integer gateMachineId) {
        return CommonResponse.success(gateMachineService.getGateMachine(gateMachineId));
    }
}
