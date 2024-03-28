package com.xtxk.cn.controller.dic;

import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.dto.dic.*;
import com.xtxk.cn.entity.DicItem;
import com.xtxk.cn.service.impl.dic.DicItemHandler;
import com.xtxk.cn.service.dic.DicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"字典管理模块"}, description = "字典表管理")
@RestController
@RequestMapping("/dic")
@Slf4j
public class DicController {

    @Autowired
    private DicService dicService;

    @Autowired
    private DicItemHandler dicItemHandler;

    @ApiOperation(value = "字典元素列表")
    @PostMapping("/list")
    public CommonResponse<DicListRespDto> list(@Valid @RequestBody DicListReqDto dicListReqDto) {
        DicListRespDto dicListRespDto = dicService.list(dicListReqDto);
        return CommonResponse.success(dicListRespDto);
    }

    @ApiOperation(value = "根据id查询详情")
    @PostMapping("/detail")
    public CommonResponse<DicDetailRespDto> detail(@Valid @RequestBody DicDetailReqDto dicDetailReqDto) {
       DicDetailRespDto dicDetailRespDto = dicService.detail(dicDetailReqDto.getId());
        return CommonResponse.success(dicDetailRespDto);
    }

    @ApiOperation(value = "字典元素下拉列表")
    @PostMapping("/superiorList")
    public CommonResponse<DicSuperiorListRespDto> superiorList() {
       DicSuperiorListRespDto dicSuperiorListRespDto = dicService.superiorList();
        return CommonResponse.success(dicSuperiorListRespDto);
    }

    @ApiOperation(value = "上级字典项下拉列表")
    @PostMapping("/dictionaryEntryList")
    public CommonResponse<DictionaryEntryRespDto> dictionaryEntryList(@Valid @RequestBody DictionaryEntryReqDto dictionaryEntryReqDto) {
        DictionaryEntryRespDto dictionaryEntryRespDto = dicService.dictionaryEntryList(dictionaryEntryReqDto.getDicParentCode());
        return CommonResponse.success(dictionaryEntryRespDto);
    }

    @ApiOperation(value = "字典元素新增")
    @PostMapping("/add")
    public CommonResponse<DicDetailRespDto> add(@Valid @RequestBody DicAddReqDto dicAddReqDto) {
       dicService.add(dicAddReqDto);
        return CommonResponse.success("新增成功");
    }

    @ApiOperation(value = "字典元素更新")
    @PostMapping("/update")
    public CommonResponse update(@Valid @RequestBody DicUpdateReqDto dicUpdateReqDto) {
       dicService.update(dicUpdateReqDto);
        return CommonResponse.success("更新成功");
    }

    @ApiOperation(value = "查询字典下的所有item")
    @GetMapping("/{dicCode}")
    public CommonResponse<List<DicItem>> getAllDistrcitDicItem(@PathVariable("dicCode") @ApiParam(value = "字典code", required = true) String dicCode) {
        return CommonResponse.success(dicItemHandler.getAllDicItemByDicCode(dicCode));
    }

    @ApiOperation(value = "获取所有算法事件dictItem")
    @GetMapping("/queryEventDictItem")
    public CommonResponse<List<EventDictItemDto>> queryEventDictItem() {
        List<EventDictItemDto> eventDictItemDtos = this.dicService.queryEventDictItem();
        return CommonResponse.success(eventDictItemDtos);
    }
}
