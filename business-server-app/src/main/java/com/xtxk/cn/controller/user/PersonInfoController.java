package com.xtxk.cn.controller.user;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageInfo;
import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.common.PageRspDto;
import com.xtxk.cn.dto.carInfo.CarFlowRspDto;
import com.xtxk.cn.dto.carInfo.ResourceCntWithTypeDto;
import com.xtxk.cn.dto.personInfo.PersonFlowRspDto;
import com.xtxk.cn.dto.personInfo.PersonInfoDto;
import com.xtxk.cn.dto.personInfo.SearchInfoDTO;
import com.xtxk.cn.dto.personInfo.VioInfoDto;
import com.xtxk.cn.entity.PersonInfo;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.service.user.PersonInfoService;
import com.xtxk.cn.service.impl.user.PersonInfoListener;
import com.xtxk.cn.utils.file.FileUploadUtils;
import com.xtxk.cn.utils.file.MimeTypeUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;


@Api(tags = {"档案信息管理-人口档案模块"}, description = "人口信息表管理")
@RestController
@RequestMapping("/personInfo")
@Validated
@AspectLogger
@Slf4j
public class PersonInfoController {

    @Autowired
    private PersonInfoService personInfoService;

    @GetMapping("findPersonInfoList")
    @ApiOperation(value = "查询人员档案信息")
    public CommonResponse<PageRspDto<PersonInfo>> findPersonInfoList(SearchInfoDTO searchInfoDTO) {
        PageInfo info = personInfoService.findPersonInfoList(searchInfoDTO);
        return CommonResponse.success(new PageRspDto<PersonInfo>(info.getTotal(), info.getPages(), info.getList()));
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出人员档案信息")
    public void export(SearchInfoDTO searchInfoDTO, HttpServletResponse response) {
        PageInfo info = personInfoService.findPersonInfoList(searchInfoDTO);
        List<PersonInfo> list = info.getList();
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(System.currentTimeMillis()
                    + "人员档案信息", "UTF-8") + ".xlsx");
            EasyExcel.write(response.getOutputStream())
                    .head(PersonInfo.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("人员档案信息")
                    .doWrite(list);
        } catch (IOException e) {
            throw new ServiceException(ErrorCode.ERROR, "导出报表异常");
        }
    }

    @PostMapping("savePersonInfo")
    @ApiOperation(value = "保存人员档案")
    public CommonResponse<Boolean> savePersonInfo(@Valid @RequestBody PersonInfoDto personInfoDto) {
        return CommonResponse.success(personInfoService.savePersonInfo(personInfoDto));
    }


    @PostMapping("/uploadImg")
    @ApiOperation(value = "保存用户图片")
    public CommonResponse<String> uploadFile(MultipartFile file) throws Exception {
        return CommonResponse.success("保存成功",personInfoService.uploadFile(file));
    }


    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除人员档案信息")
    public CommonResponse<Boolean> deleteById(@PathVariable Integer id) {
        return CommonResponse.success(personInfoService.deleteById(id));
    }

    @GetMapping("/preview/{year}/{month}/{day}/{fileName:.+}")
    @ApiOperation(value = "人员图片预览")
    public void preview(@PathVariable String year, @PathVariable String month,
                        @PathVariable String day, @PathVariable String fileName,
                        HttpServletResponse response) throws IOException {
        String filePath = FileUploadUtils.getDefaultBaseDir() + File.separator + year + File.separator + month + File.separator + day + File.separator + fileName;
        OutputStream out = null;
        response.setHeader("Cache-Control", "no-store,no-cache");
        String formatName = "";
        if (fileName.toLowerCase().endsWith("png")) {
            response.setContentType(MimeTypeUtils.IMAGE_PNG);
            formatName = "png";
        } else if (fileName.toLowerCase().endsWith("jpg") || fileName.toLowerCase().endsWith("jpeg")) {
            response.setContentType(MimeTypeUtils.IMAGE_JPG);
            formatName = "jpg";
        } else {
            response.setContentType("application/octet-stream");
        }
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            out = response.getOutputStream();
            ImageIO.write(image, formatName, out);
        } catch (Exception e) {
            log.error("读取文件异常,文件路径{}", filePath);
        } finally {
            if (out != null) {
                out.flush();
                IOUtils.closeQuietly(out);
            }
        }
    }


    @GetMapping("/downloadTemplate")
    @ApiOperation(value = "人员数据模板下载")
    public void download(HttpServletResponse response) {
        InputStream inputStream = null;
        BufferedInputStream bis = null;
        OutputStream outputStream = null;
        try {
            ClassPathResource resource = new ClassPathResource("excel/人员档案模板.xlsx");
            inputStream = resource.getInputStream();

            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("人员档案模板.xlsx", "UTF-8"));

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
            log.error("读取excel模板异常,异常信息{}", e.getMessage());
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(bis);
        }
    }


    @PostMapping("/importData")
    @ApiOperation(value = "导入用户档案数据")
    public CommonResponse<Boolean> importData(MultipartFile file) throws Exception {
        PersonInfoListener listener = new PersonInfoListener(personInfoService);
        EasyExcel.read(file.getInputStream(), PersonInfo.class, listener).sheet().headRowNumber(1).doRead();
        return CommonResponse.success();
    }

    @DeleteMapping("/deleteBatch")
    @ApiOperation(value = "批量删除人员档案信息")
    public CommonResponse<Boolean> deleteBatch(@RequestParam String ids) {
        return CommonResponse.success(personInfoService.deleteBatch(ids));
    }

    @GetMapping("/queryCarFlow")
    @ApiOperation(value = "查询车辆流量")
    public CommonResponse<CarFlowRspDto> queryCarFlow(@RequestParam(required = false)  @ApiParam Date beginDate, @RequestParam(required = false)  @ApiParam Date endDate) {
        CarFlowRspDto carFlowRspDtos = this.personInfoService.queryCarFlow(beginDate, endDate);
        return CommonResponse.success(carFlowRspDtos);
    }

    @GetMapping("/queryPersonFlow")
    @ApiOperation(value = "查询人流量")
    public CommonResponse<PersonFlowRspDto> queryPersonFlow(@RequestParam(required = false) @ApiParam Date beginDate, @RequestParam(required = false) @ApiParam Date endDate) {
        PersonFlowRspDto personFlowRspDtos = this.personInfoService.queryPersonFlow(beginDate, endDate);
        return CommonResponse.success(personFlowRspDtos);
    }

    @GetMapping("/queryViolationInfoByTargetId")
    @ApiOperation(value = "通过主体查询违规信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type" ,value = "违规类型（1：违规车辆;2：违规人员3;3.违规楼栋）", required = true),
            @ApiImplicitParam(name = "targetId" ,value = "如果是车辆，填写车牌号，如果是人员填写人员id", required = true)
    })
    public CommonResponse<List<VioInfoDto>> queryViolationInfoByTargetId(@RequestParam(required = false) Integer type, @RequestParam(required = false)String targetId) {
        List<VioInfoDto> vioInfoDtos = this.personInfoService.queryViolationInfoByTargetId(targetId, type);
        return CommonResponse.success(vioInfoDtos);
    }

    @GetMapping("/queryPersonCntWithType")
    @ApiOperation(value = "人员信息统计")
    public CommonResponse<List<ResourceCntWithTypeDto>> queryPersonCntWithType() {
        List<ResourceCntWithTypeDto> resourceCntWithTypeDtos = personInfoService.queryPersonCntWithType();
        return CommonResponse.success(resourceCntWithTypeDtos);
    }
}
