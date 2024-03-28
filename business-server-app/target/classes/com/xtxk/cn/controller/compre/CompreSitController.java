package com.xtxk.cn.controller.compre;

import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.entity.HousingEstateSitDto;
import com.xtxk.cn.service.compre.CompreSitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"综合态势模块"}, description = "综合态势")
@RestController
@RequestMapping("/compreSit")
@AspectLogger
public class CompreSitController {
    @Autowired
    private CompreSitService compreSitService;

    @ApiOperation(value = "获取小区概况")
    @GetMapping("/housingEstateSit")
    public CommonResponse<HousingEstateSitDto> getHousingEstateSit() {
        return CommonResponse.success(compreSitService.getHousingEstateSit());
    }
}
