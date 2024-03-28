package com.xtxk.cn.controller.car;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageInfo;
import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.common.PageRspDto;
import com.xtxk.cn.dto.carInfo.ResourceCntWithTypeDto;
import com.xtxk.cn.dto.carInfo.SearchCarDTO;
import com.xtxk.cn.entity.CarInfo;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.service.car.CarInfoService;
import com.xtxk.cn.service.impl.car.CarInfoListener;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@Api(tags = {"档案信息管理-车辆档案"})
@RestController
@RequestMapping("/carInfo")
@Validated
@AspectLogger
public class CarInfoController {

    @Autowired
    private CarInfoService carInfoService;

    @GetMapping("findCarInfoList")
    @ApiOperation(value = "查询车辆档案信息")
    public CommonResponse<PageRspDto<CarInfo>> findCarInfoList(SearchCarDTO searchCarDTO) {
        PageInfo info = carInfoService.findByPage(searchCarDTO);
        return CommonResponse.success(new PageRspDto<CarInfo>(info.getTotal(), info.getPages(), info.getList()));
    }


    @PostMapping("/saveCarInfo")
    @ApiOperation(value = "保存车辆档案信息")
    public CommonResponse<Boolean> saveCarInfo(@Valid @RequestBody CarInfo carInfo) {
        return CommonResponse.success(carInfoService.saveCarInfo(carInfo));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除车辆档案信息")
    public CommonResponse<Boolean> deleteById(@PathVariable Integer id) {
        return CommonResponse.success(carInfoService.deleteById(id));
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出车辆档案信息")
    public void export(SearchCarDTO searchCarDTO, HttpServletResponse response) {
        PageInfo info = carInfoService.findByPage(searchCarDTO);
        List<CarInfo> list = info.getList();
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(System.currentTimeMillis()
                    + "车辆档案信息", "UTF-8") + ".xlsx");
            EasyExcel.write(response.getOutputStream())
                    .head(CarInfo.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("车辆档案信息")
                    .doWrite(list);
        } catch (IOException e) {
            throw new ServiceException(ErrorCode.ERROR, "导出报表异常");
        }
    }

    @GetMapping("/downloadTemplate")
    @ApiOperation(value = "车辆数据模板下载")
    public void download(HttpServletResponse response) {
        InputStream inputStream = null;
        BufferedInputStream bis = null;
        OutputStream outputStream = null;
        try {
            ClassPathResource resource = new ClassPathResource("excel/车辆档案模板.xlsx");
            inputStream = resource.getInputStream();
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("车辆档案模板.xlsx", "UTF-8"));

            outputStream = response.getOutputStream();
            byte[] buff = new byte[1024];
            outputStream = response.getOutputStream();
            bis = new BufferedInputStream(inputStream);
            int read = bis.read(buff);
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
            response.setHeader("Content-Length", String.valueOf(buff.length));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(bis);
        }
    }


    @PostMapping("/importData")
    @ApiOperation(value = "导入车辆档案数据")
    public CommonResponse<Boolean> importData(MultipartFile file) throws Exception {
        CarInfoListener listener = new CarInfoListener(carInfoService);
        EasyExcel.read(file.getInputStream(), CarInfo.class, listener).sheet().headRowNumber(1).doRead();
        return CommonResponse.success();
    }


    @DeleteMapping("/deleteBatch")
    @ApiOperation(value = "批量删除车辆档案信息")
    public CommonResponse<Boolean> deleteBatch(@RequestParam String ids) {
        return CommonResponse.success(carInfoService.deleteBatch(ids));
    }

    @GetMapping("/queryCarCntWithType")
    @ApiOperation(value = "车辆信息统计")
    public CommonResponse<List<ResourceCntWithTypeDto>> queryCarCntWithType() {
        List<ResourceCntWithTypeDto> resourceCntWithTypeDtos = carInfoService.queryCarCntWithType();
        return CommonResponse.success(resourceCntWithTypeDtos);
    }

}
