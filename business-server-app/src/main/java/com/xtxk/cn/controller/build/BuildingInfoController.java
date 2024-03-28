package com.xtxk.cn.controller.build;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageInfo;
import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.common.PageRspDto;
import com.xtxk.cn.dto.build.BuildingRelatedDeviceDto;
import com.xtxk.cn.dto.geogra.AddBuildingInfoReqDto;
import com.xtxk.cn.dto.geogra.BuildingLocationInfo;
import com.xtxk.cn.dto.geogra.UpdateBuildingInfoReqDto;
import com.xtxk.cn.dto.build.BuildingDeviceDto;
import com.xtxk.cn.entity.BuildingInfoPO;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.service.build.BuildingInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Api(tags = {"地理信息管理模块-楼栋建筑"}, description = "楼栋建筑")
@RestController
@RequestMapping("/buildingInfo")
@AspectLogger
public class BuildingInfoController {
    @Autowired
    private BuildingInfoService buildingInfoService;

    @GetMapping("/page")
    @ApiOperation(value = "分页查询楼栋建筑")
    public CommonResponse<PageRspDto<BuildingInfoPO>> getBuildingInfoList(@RequestParam(value = "keyWord", required = false) @ApiParam(value = "关键字") String keyWord,
                                                                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") @ApiParam(value = "当前页数") Integer pageNum,
                                                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "每页条数") Integer pageSize) {
        PageInfo<BuildingInfoPO> pageInfo = buildingInfoService.getBuildingInfoList(keyWord, pageNum, pageSize);
        return CommonResponse.success(new PageRspDto<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList()));
    }

    @ApiOperation(value = "新增楼栋建筑")
    @PostMapping
    public CommonResponse addBuildingInfo(@Valid @RequestBody AddBuildingInfoReqDto addBuildingInfoReqDto) {
        buildingInfoService.addBuildingInfo(addBuildingInfoReqDto);
        return CommonResponse.success("新增成功");
    }

    @ApiOperation(value = "修改楼栋建筑信息")
    @PutMapping("/{buildingId}")
    public CommonResponse updateBuildingInfo(@PathVariable("buildingId") @ApiParam(value = "楼栋建筑id", required = true) Integer buildingId, @Valid @RequestBody UpdateBuildingInfoReqDto updateBuildingInfoReqDto) {
        if (null == buildingId) {
            throw new ServiceException(ErrorCode.BUILDING_ID_NULL);
        }
        buildingInfoService.updateBuildingInfo(buildingId, updateBuildingInfoReqDto);
        return CommonResponse.success("修改成功");
    }

    @ApiOperation(value = "删除楼栋建筑信息")
    @DeleteMapping("/{buildingId}")
    public CommonResponse deleteBuildingInfo(@PathVariable("buildingId") @ApiParam(value = "楼栋建筑id", required = true) Integer buildingId) {
        if (null == buildingId) {
            throw new ServiceException(ErrorCode.BUILDING_ID_NULL);
        }
        buildingInfoService.deleteBuildingInfo(buildingId);
        return CommonResponse.success("删除成功");
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出楼栋建筑信息")
    public void exportBuildingInfo(@RequestParam(value = "keyWord", required = false) @ApiParam(value = "关键字") String keyWord, HttpServletResponse response) {
        List<BuildingInfoPO> list = buildingInfoService.getExportBuildingInfoList(keyWord);
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(System.currentTimeMillis()
                    + "楼栋建筑信息", "UTF-8") + ".xlsx");
            EasyExcel.write(response.getOutputStream())
                    .head(BuildingInfoPO.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("楼栋建筑信息")
                    .doWrite(list);
        } catch (IOException e) {
            throw new ServiceException(ErrorCode.ERROR, "导出报表异常");
        }
    }

    @GetMapping
    @ApiOperation(value = "查询单个楼栋建筑")
    public CommonResponse<BuildingInfoPO> getBuildingInfo(@RequestParam(value = "buildingId") @ApiParam(value = "楼栋id", required = true) Integer buildingId) {
        return CommonResponse.success(buildingInfoService.getBuildingInfo(buildingId));
    }


    @GetMapping(value = "/getBuildingInfoByIdentity")
    @ApiOperation(value = "查询单个楼栋建筑信息根据地图标识")
    public CommonResponse<BuildingInfoPO> getBuildingInfoByIdentity(@RequestParam(value = "identityId") @ApiParam(value = "标识id", required = true) String identityId) {
        return CommonResponse.success(buildingInfoService.getBuildingInfoByIdentity(identityId));
    }


    @GetMapping("/all/locationInfo")
    @ApiOperation(value = "查询所有楼栋建筑位置坐标")
    public CommonResponse<List<BuildingLocationInfo>> getAllBuildingLocationInfo() {
        return CommonResponse.success(buildingInfoService.getAllBuildingLocationInfo());
    }


    @GetMapping("/getBuildingDeviceListByIdentity")
    @ApiOperation(value = "根据identityId查询楼栋关联的设备列表")
    public CommonResponse<BuildingDeviceDto> getBuildingDeviceListByIdentity(@RequestParam(value = "identityId") @ApiParam(value = "标识id", required = true) String identityId) {
        return CommonResponse.success(buildingInfoService.getBuildingDeviceListByIdentity(identityId));
    }

    @GetMapping("/getBuildingRelatedDeviceList")
    @ApiOperation(value = "查询楼栋列表(包含楼栋关联设备数量及状态)")
    public CommonResponse<List<BuildingRelatedDeviceDto>> getBuildingRelatedDeviceList() {
        return CommonResponse.success(buildingInfoService.getBuildingRelatedDeviceList());
    }

}
