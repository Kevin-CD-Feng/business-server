package com.xtxk.recognition.prepare.service.web;


import cn.hutool.core.util.StrUtil;
import com.xtxk.recognition.prepare.service.algAction.*;
import com.xtxk.recognition.prepare.service.common.CommonResponse;
import com.xtxk.recognition.prepare.service.component.annotation.AspectLogger;
import com.xtxk.recognition.prepare.service.dto.AlarmPolicyConfiguration.AddAlarmPolicyConfigurationReqDto;
import com.xtxk.recognition.prepare.service.dto.AlarmPolicyConfiguration.AlarmPolicyConfigurationItemDto;
import com.xtxk.recognition.prepare.service.dto.loopAlg.*;
import com.xtxk.recognition.prepare.service.dto.loopAlg.LoopResource;
import com.xtxk.recognition.prepare.service.entity.AlarmPolicyConfiguration;
import com.xtxk.recognition.prepare.service.entity.DicItem;
import com.xtxk.recognition.prepare.service.enums.ErrorCode;
import com.xtxk.recognition.prepare.service.mapper.AlarmPolicyConfigurationMapper;
import com.xtxk.recognition.prepare.service.mapper.DicItemMapper;
import com.xtxk.recognition.prepare.service.mapper.LoopAlgResMapper;
import com.xtxk.recognition.prepare.service.svc.AlarmPolicyConfigurationService;
import com.xtxk.recognition.prepare.service.svc.SimilarityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Api(tags = {"视频智能分析-算法轮询配置"})
@RestController
@RequestMapping("/loopAlg")
@AspectLogger
public class LoopAlgController {

    @Autowired
    private LoopAlgResMapper loopAlgResMapper;

    @Autowired
    private AlgDispatcher algDispatcher;

    @Autowired
    private SimilarityService similarityService;

    @Autowired
    private AlarmPolicyConfigurationMapper alarmPolicyConfigurationMapper;

    @Autowired
    private DicItemMapper dicItemMapper;

    @Autowired
    private AlarmPolicyConfigurationService alarmPolicyConfigurationService;

    @GetMapping("/queryloopRes")
    @ApiOperation(value = "根据算法查询轮询资源")
    public CommonResponse<List<LoopAlgResRspDto>> queryloopRes(@RequestParam String algCode, @RequestParam String eventCode, @RequestParam(required = false) String keyWords) {
        List<LoopAlgResVo> loopAlgResRspDtos = this.loopAlgResMapper.queryloopRes(algCode, eventCode, keyWords);
        HashMap<String, LoopAlgResRspDto> rspDto = new HashMap<>();
        loopAlgResRspDtos.forEach(it -> {
            if (!rspDto.containsKey(it.getLoopId())) {
                LoopAlgResRspDto dto = new LoopAlgResRspDto();
                dto.setAlgCode(it.getAlgCode());
                dto.setEventCode(it.getEventCode());
                dto.setAlgOrder(it.getAlgOrder());
                dto.setIsEnable(it.getIsEnable());
                dto.setLoopId(it.getLoopId());
                dto.setLoopTime(it.getLoopTime());
                dto.setResources(new ArrayList<>());
                rspDto.put(it.getLoopId(), dto);
            }
            LoopAlgResRspDto dto = rspDto.get(it.getLoopId());
            LoopResource device = new LoopResource();
            device.setResourceId(it.getResourceId());
            device.setResourceName(it.getResourceName());
            dto.getResources().add(device);
            //rspDto.put(it.getLoopId(),)
        });
        List<LoopAlgResRspDto> collect = rspDto.values().stream().sorted(Comparator.comparingInt(LoopAlgResRspDto::getAlgOrder))
                .collect(Collectors.toList());
        return CommonResponse.success(collect);
    }

    @ApiOperation(value = "新增轮询设备")
    @PostMapping("/addLoopRes")
    @Transactional(rollbackFor = Exception.class)
    public CommonResponse addLoopRes(@Valid @RequestBody LoopAlgResReqDto loopAlgResReqDto) {
        if (loopAlgResReqDto.getLoopTime() <= 0)
            return CommonResponse.error(ErrorCode.PARAMS_ERROR, "loopTime必须大于一个小时.");
        String loopId = UUID.randomUUID().toString();
        loopAlgResReqDto.setLoopId(loopId);
        loopAlgResReqDto.setIsEnable(true);
        Integer max = this.loopAlgResMapper.queryMaxOrder(loopAlgResReqDto.getAlgCode(), loopAlgResReqDto.getEventCode());
        Integer orders = Optional.ofNullable(max).orElse(0);
        loopAlgResReqDto.setAlgOrder(orders + 1);
        this.loopAlgResMapper.addLoopRes(loopAlgResReqDto);

        if (loopAlgResReqDto.getResourceIds().size() > 0) {
            //告警策略设备数据写入
            addAndUpdateAlarmPolicy(loopAlgResReqDto);
        }

        List<AlgLoopModelByConditionDto> algLoopModelByConditionDtos = this.loopAlgResMapper.queryAlgLoopModelByCondition(null, loopId);
        refreshDispacherBucket(algLoopModelByConditionDtos);
        return CommonResponse.success("新增成功");
    }

    private void refreshDispacherBucket(List<AlgLoopModelByConditionDto> algLoopModelByConditionDtos) {
        //algLoopModelByConditionDtos.removeIf(it->Strings.isEmpty(it.getWebCoordinate()));
        HashMap<String, AlgLoopModel> cur = new HashMap<>();

        List<DicItem> algorithm_name = dicItemMapper.queryDicItemListByDicCode("algorithm_name");
        Map<String, DicItem> algorithmMap = algorithm_name.stream().collect(Collectors.toMap(DicItem::getItemCode, it -> it));

        algLoopModelByConditionDtos.forEach(item -> {
            String[] loopKeys = {item.getAlgCode().trim(), item.getEventCode().trim(), String.valueOf(item.getAlgOrder())};
            String innerKey = String.join("_", loopKeys);
            if (!cur.containsKey(innerKey)) {
                AlgLoopModel algLoopModel = new AlgLoopModel();
                algLoopModel.setOrder(item.getAlgOrder());
                algLoopModel.init((int) (item.getLoopTime() * 3600), innerKey, new ArrayList<>());
                cur.put(innerKey, algLoopModel);
            }
            AlgLoopModel algLoopModel = cur.get(innerKey);
            AlgModel algModel = AlgModel.builder().build();

            algModel.setAlgCode(item.getAlgCode());
            algModel.setEventCode(item.getEventCode());
            algModel.setResourceId(item.getResourceId());
            algModel.setResourceName(item.getResourceName());
            algModel.setWebCoordinate(item.getWebCoordinate());
            algModel.setFrameInterval(item.getFrameInterval());
            algModel.setLoopTime(item.getLoopTime());
            algModel.setOrder(item.getAlgOrder());
            algModel.setResourceSipId(item.getResourceSipId());
            DicItem dicItem = algorithmMap.get(item.getAlgCode());
            algModel.setAlgorithmName(dicItem.getItemName());

            algLoopModel.setContext(innerKey);


            Integer violationDuration = Optional.ofNullable(item.getViolationDuration()).orElse(0);
            Integer violationInterval = Optional.ofNullable(item.getViolationInterval()).orElse(0);
            //数据库中是分钟为单位，传值修改为秒
            algModel.setDuration(violationDuration);
            algModel.setInterval(violationInterval);
            algLoopModel.getModels().add(algModel);
        });
        //现刷新一下，放置新增新的算法
        this.algDispatcher.refreshBuckets();

        cur.forEach((k, v) -> {
            String bucketName = StrUtil.subBefore(k, "_", true);
            if (this.algDispatcher.getBuckets().containsKey(bucketName)) {
                AlgBucket bucket = this.algDispatcher.getBuckets().get(bucketName);
                bucket.updateAlgLoopModel(v);
            }
        });
    }

    @ApiOperation(value = "删除轮询设备")
    @PostMapping("/delLoopRes")
    @Transactional(rollbackFor = Exception.class)
    public CommonResponse delLoopRes(@RequestParam String loopId) {
        //先查出对应的配置，从内存中将加载的轮询算法清除
        List<LoopAlgResVo> loopAlgResVos = this.loopAlgResMapper.querySingleloopRes(loopId);
        if (loopAlgResVos.size() > 0) {
            LoopAlgResVo vo = loopAlgResVos.get(0);
            String[] splits = {vo.getAlgCode().trim(), vo.getEventCode().trim()};
            String bucketName = String.join("_", splits);
            String[] actions = {vo.getAlgCode().trim(), vo.getEventCode().trim(), String.valueOf(vo.getAlgOrder())};
            String loopKey = String.join("_", actions);
            if (this.algDispatcher.getBuckets().containsKey(bucketName)) {
                AlgBucket bucket = this.algDispatcher.getBuckets().get(bucketName);
                bucket.removeAlgLoopModel(loopKey);
            }
        }

        //还要从表t_alarm_policy_configuration删除配置项
        List<String> resourceIds = new ArrayList<>();
        final String[] algCode = {""};
        final String[] eventCode = {""};
        loopAlgResVos.forEach(it -> {
            if (Strings.isEmpty(algCode[0])) {
                algCode[0] = it.getAlgCode();
            }
            if (Strings.isEmpty(eventCode[0])) {
                eventCode[0] = it.getEventCode();
            }
            resourceIds.add(it.getResourceId());
        });
        if (resourceIds.size() > 0) {
            this.alarmPolicyConfigurationMapper
                    .delAlarmPolicyByCodeAndResourceId(algCode[0], eventCode[0], resourceIds);
        }

        this.loopAlgResMapper.delLoopRes(loopId);
        return CommonResponse.success("新增成功");
    }

    @GetMapping("/querySingleloopRes")
    @ApiOperation(value = "查询单条轮询资源")
    public CommonResponse<LoopAlgResRspDto> querySingleloopRes(@RequestParam String loopId) {
        List<LoopAlgResVo> loopAlgResVo = this.loopAlgResMapper.querySingleloopRes(loopId);
        LoopAlgResRspDto dto = new LoopAlgResRspDto();
        dto.setResources(new ArrayList<>());
        loopAlgResVo.forEach(it -> {
            dto.setLoopId(it.getLoopId());
            dto.setAlgCode(it.getAlgCode());
            dto.setEventCode(it.getEventCode());
            dto.setAlgOrder(it.getAlgOrder());
            dto.setLoopTime(it.getLoopTime());
            dto.setIsEnable(it.getIsEnable());
            LoopResource device = new LoopResource();
            device.setResourceName(it.getResourceName());
            device.setResourceId(it.getResourceId());
            dto.getResources().add(device);
        });
        return CommonResponse.success(dto);
    }

    @ApiOperation(value = "编辑轮询设备")
    @PostMapping("/editLoopRes")
    @Transactional(rollbackFor = Exception.class)
    public CommonResponse editLoopRes(@Valid @RequestBody LoopAlgResReqDto loopAlgResReqDto) {
        if (loopAlgResReqDto.getLoopTime() <= 0)
            return CommonResponse.error(ErrorCode.PARAMS_ERROR, "loopTime必须大于一个小时.");

        List<String> resIDs = alarmPolicyConfigurationMapper.queryResIdByAlgCode(loopAlgResReqDto.getAlgCode());
        List<String> reqIDs = new ArrayList<>();
        reqIDs.addAll(loopAlgResReqDto.getResourceIds());
        resIDs.removeAll(reqIDs); //编辑删除的设备列表


        if (loopAlgResReqDto.getResourceIds() != null && loopAlgResReqDto.getResourceIds().size() > 0) {
            this.loopAlgResMapper.delLoopResItem(loopAlgResReqDto.getLoopId());
        }
        this.loopAlgResMapper.editLoopRes(loopAlgResReqDto);

        if (loopAlgResReqDto.getIsEnable()) {
            List<AlgLoopModelByConditionDto> algLoopModelByConditionDtos = this.loopAlgResMapper
                    .queryAlgLoopModelByCondition(null, loopAlgResReqDto.getLoopId());
            refreshDispacherBucket(algLoopModelByConditionDtos);

            if (loopAlgResReqDto.getResourceIds().size() > 0) {
                if (resIDs.size() > 0) {
                    alarmPolicyConfigurationMapper.delAlarmPolicyByCodeAndResourceId(loopAlgResReqDto.getAlgCode(), loopAlgResReqDto.getEventCode(), resIDs);
                }
                //告警策略设备数据写入
                addAndUpdateAlarmPolicy(loopAlgResReqDto);
            }
        } else {
            //如果是禁用，表现就是和删除一致
            String loopId = loopAlgResReqDto.getLoopId();
            List<LoopAlgResVo> loopAlgResVos = this.loopAlgResMapper.querySingleloopRes(loopId);
            if (loopAlgResVos.size() > 0) {
                LoopAlgResVo vo = loopAlgResVos.get(0);
                String[] splits = {vo.getAlgCode().trim(), vo.getEventCode().trim()};
                String bucketName = String.join("_", splits);
                String[] actions = {vo.getAlgCode().trim(), vo.getEventCode().trim(), String.valueOf(vo.getAlgOrder())};
                String loopKey = String.join("_", actions);
                if (this.algDispatcher.getBuckets().containsKey(bucketName)) {
                    AlgBucket bucket = this.algDispatcher.getBuckets().get(bucketName);
                    bucket.removeAlgLoopModel(loopKey);
                }
            }
        }
        return CommonResponse.success("编辑成功");
    }

    @ApiOperation(value = "排序轮询设备")
    @PostMapping("/swapLoopRes")
    @Transactional(rollbackFor = Exception.class)
    public CommonResponse swapLoopRes(@RequestParam String first, @RequestParam String second) {
        List<LoopAlgResVo> loopAlgResVos = this.loopAlgResMapper.querySingleloopRes(first);
        List<LoopAlgResVo> loopAlgResVos1 = this.loopAlgResMapper.querySingleloopRes(second);
        SwapLoopResReqVo vo = new SwapLoopResReqVo();
        vo.setFirst(first);
        vo.setFirstOrder(loopAlgResVos1.get(0).getAlgOrder());
        vo.setSecond(second);
        vo.setSecondOrder(loopAlgResVos.get(0).getAlgOrder());
        this.loopAlgResMapper.swapLoopRes(vo);

        //交换顺序，更新
//        List<AlgLoopModelByConditionDto> firstDto = this.loopAlgResMapper
//                .queryAlgLoopModelByCondition(first, null);
//        refreshDispacherBucket(firstDto);
//        List<AlgLoopModelByConditionDto> secondDto = this.loopAlgResMapper
//                .queryAlgLoopModelByCondition(second, null);
//        refreshDispacherBucket(secondDto);
        return CommonResponse.success("交换成功");
    }

    @ApiOperation(value = "显示内存中所有的运行状态")
    @GetMapping("/displayAlg")
    public CommonResponse displayAlg() {
        List<AlgBucketDisplayDto> dtos = new ArrayList<>();
        this.algDispatcher.getBuckets().forEach((k, v) -> {
            AlgBucketDisplayDto dto = new AlgBucketDisplayDto();
            dto.setBucketName(v.getBucketName());
            dto.setResources(new ArrayList<>());
            dto.setActions(new HashMap<>());
            dto.setCurrentLoop(null);
            v.getResources().forEach(it -> {
                dto.getResources().add(it);
            });
            v.getActions().forEach((ik, iv) -> {
                dto.getActions().put(ik, iv.getAlgModel());
            });
            dto.setCurrentLoop(v.getCurrentLoop().peekFirst());
            dtos.add(dto);
        });
        return CommonResponse.success(dtos);
    }


    @ApiOperation(value = "保存人脸识别特征值")
    @PostMapping("/saveFeatures")
    public CommonResponse saveFeatures(@RequestBody FeatureDto featureDto) {
        this.similarityService.saveSimilarity(featureDto.getPersonId(), featureDto.getFeature());
        return CommonResponse.success("操作成功");
    }


    //告警策略设备数据写入
    private void addAndUpdateAlarmPolicy(@RequestBody @Valid LoopAlgResReqDto loopAlgResReqDto) {
        for (String resourceID : loopAlgResReqDto.getResourceIds()) {


            AlarmPolicyConfiguration alarmPolicyConfiguration = alarmPolicyConfigurationMapper.queryById(resourceID);
            if (alarmPolicyConfiguration != null && !alarmPolicyConfiguration.getAlgCode().equals("") && alarmPolicyConfiguration.getAlgCode().equals(loopAlgResReqDto.getAlgCode())) {
                continue;
            }

            AddAlarmPolicyConfigurationReqDto addAlarmPolicyConfigurationReqDto = new AddAlarmPolicyConfigurationReqDto();
            List<AlarmPolicyConfigurationItemDto> alarmPolicyConfigurationItemDtoList = new ArrayList<>();
            AlarmPolicyConfigurationItemDto alarmPolicyConfigurationItemDto = new AlarmPolicyConfigurationItemDto();
            alarmPolicyConfigurationItemDto.setResourceId(resourceID);
            alarmPolicyConfigurationItemDto.setAlgCode(loopAlgResReqDto.getAlgCode());
            alarmPolicyConfigurationItemDto.setEventCode(loopAlgResReqDto.getEventCode());
            alarmPolicyConfigurationItemDtoList.add(alarmPolicyConfigurationItemDto);
            addAlarmPolicyConfigurationReqDto.setAlarmPolicyConfigurationItemDtoList(alarmPolicyConfigurationItemDtoList);
            alarmPolicyConfigurationService.addAndUpdate(addAlarmPolicyConfigurationReqDto);
        }
    }
}