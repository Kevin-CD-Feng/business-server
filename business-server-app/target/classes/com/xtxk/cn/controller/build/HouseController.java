package com.xtxk.cn.controller.build;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.dto.build.HouseParams;
import com.xtxk.cn.dto.build.HouseVo;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.service.build.HouseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Api(tags = {"基本信息管理-房屋信息"})
@RestController
@RequestMapping("/houseInfo")
@AspectLogger
public class HouseController {
    @Autowired
    private HouseInfoService houseInfoService;

    @GetMapping("/page")
    @ApiOperation(value = "分页查询房屋信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true),
            @ApiImplicitParam(name = "keyWord", value = "关键词"),
            @ApiImplicitParam(name = "buildId", value = "楼栋id"),
            @ApiImplicitParam(name = "houseType", value = "房屋类型"),
            @ApiImplicitParam(name = "area", value = "区域")
    })
    public CommonResponse<HouseVo> buildingInfoList(@ApiIgnore HouseParams houseParams) {
        return CommonResponse.success(houseInfoService.buildingInfoList(houseParams));
    }


    @GetMapping("/export")
    @ApiOperation(value = "导出房屋信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true),
            @ApiImplicitParam(name = "keyWord", value = "关键词"),
            @ApiImplicitParam(name = "buildId", value = "楼栋id"),
            @ApiImplicitParam(name = "houseType", value = "房屋类型"),
            @ApiImplicitParam(name = "area", value = "区域")
    })
    public void export(@ApiIgnore HouseParams houseParams, HttpServletResponse response) {
        List<HouseVo> list = houseInfoService.export(houseParams);
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(System.currentTimeMillis()
                    + "房屋信息导出", "UTF-8") + ".xlsx");
            EasyExcel.write(response.getOutputStream())
                    .head(HouseVo.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("房屋信息")
                    .doWrite(list);
        } catch (IOException e) {
            throw new ServiceException(ErrorCode.ERROR, "导出报表异常");
        }
    }

}
