package com.xtxk.cn.controller.device;

import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.JSONObject;
import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.common.*;
import com.xtxk.cn.dto.monitor.*;
import com.xtxk.cn.entity.DicItem;
import com.xtxk.cn.enums.DeviceStateEnum;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.mapper.DicItemMapper;
import com.xtxk.cn.mapper.MonitorDeviceMapper;
import com.xtxk.cn.utils.json.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Api(tags = {"设备信息表模块"}, description = "设备信息表管理")
@RestController
@RequestMapping("/deviceInfo")
@AspectLogger
@Slf4j
public class DeviceInfoController {

    @Value("${third.userId:}")
    private String tmpConfigUserId;

    @Autowired
    private ThridConfig thridConfig;

    @Autowired
    private MonitorDeviceMapper monitorDeviceMapper;

    @Autowired
    private DicItemMapper dicItemMapper;

    private RestTemplate restTemplate = new RestTemplate();


    private Map<String, DeviceStateDto> getDeviceStateFromOpenVone(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("departmentId", thridConfig.getDirectoryId());
        jsonObject.put("userID", userId);
        jsonObject.put("key", "");
        String types = thridConfig.getQueryTypes();
        jsonObject.put("queryTypes", Arrays.asList(types.split(",")));

        Map<String, String> body = new LinkedHashMap<>();
        body.put("endPoint", "/resourcebussinessserviceproto.ResourceBusinessService/QueryOrganizationResourcesByUserID2");
        body.put("service", "resource-business-service:8080");
        body.put("param", jsonObject.toString());
        body.put("token", "");
        body.put("nodeId", "");
        body.put("serviceKey", "");

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<DeviceStateResult> response = restTemplate.postForEntity(thridConfig.getDatacenter(), httpEntity, DeviceStateResult.class);
        if (response == null || response.getStatusCode() != HttpStatus.OK) {
            throw new ServiceException(ErrorCode.ERROR, response.getBody().getResponseDesc());
        }
        Map<String, DeviceStateDto> map = new HashMap<>();
        DeviceStateResult dsr = response.getBody();
        if (null == dsr) {
            return map;
        }
        List resources = dsr.getResources();
        if (!CollectionUtils.isEmpty(resources)) {
            List<DeviceStateDto> deviceStateDtos = JsonUtil.str2List(JsonUtil.obj2Str(resources), DeviceStateDto.class);
            deviceStateDtos.forEach(e -> {
                map.put(e.getResourceID(), e);
            });
        }
        return map;
    }


    @GetMapping("/findDeviceList")
    @ApiOperation("监控设备数据")
    public CommonResponse<List<DeviceDTO>> findDeviceList(@RequestParam(value = "deviceName", required = false) @ApiParam(value = "设备名称") String deviceName,
                                                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") @ApiParam(value = "当前页数") Integer pageNum,
                                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "每页条数") Integer pageSize) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        JSONObject params = new JSONObject();
        params.put("departmentID", thridConfig.getDirectoryId());
        params.put("deviceName", deviceName);
        String types = thridConfig.getQueryTypes();
        params.put("queryTypes", Arrays.asList(types.split(",")));

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("version", "v1.0");
        body.add("funcName", "QueryResourcesByDepartmentIDCommon");
        body.add("operatorToken", thridConfig.getOperatorToken());
        body.add("serviceName", "AdminService");
        body.add("nodeID", thridConfig.getNodeId());
        body.add("params", params.toString());
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);


        String userId = tmpConfigUserId;
        Map<String, DeviceStateDto> map = getDeviceStateFromOpenVone(userId);
        List<DeviceDTO> result = null;
        try {
            ResponseEntity<DataApiResult> response = restTemplate.postForEntity(thridConfig.getDataRequestForWeb(), httpEntity, DataApiResult.class);
            if (response == null || response.getStatusCode() != HttpStatus.OK) {
                throw new ServiceException(ErrorCode.ERROR, response.getBody().getResponseDesc());
            }
            if (response.getBody() != null) {
                List<HashMap> returnVal = response.getBody().getReturnValue();
                List<String> deviceIdList = monitorDeviceMapper.getAllDeviceId();
                List<HashMap> existDeviceIdList = new ArrayList<>();
                returnVal.forEach(e -> {
                    String resourceId = (String) e.get("resourceID");
                    if (deviceIdList.contains(resourceId)) {
                        existDeviceIdList.add(e);
                    }
                });
                returnVal.removeAll(existDeviceIdList);
                // 分页
                List pageList = ListUtil.page(pageNum - 1, pageSize, returnVal);
                // 记录总数
                int count = returnVal.size();
                // 分页总数
                int pageCount = (count + pageSize - 1) / pageSize;
                result = JsonUtil.str2List(JsonUtil.obj2Str(pageList), DeviceDTO.class);
                if (!CollectionUtils.isEmpty(result)) {
                    result.forEach(e -> {
                        String resourceID = e.getResourceID();
                        DeviceStateDto deviceStateDto = map.get(resourceID);
                        if (null != deviceStateDto) {
                            e.setDeviceState(getDeviceState(deviceStateDto.getStatus()));
                            e.setLongitude(deviceStateDto.getLongitude());
                            e.setLatitude(deviceStateDto.getLatitude());
                        }
                        String deviceType = e.getDeviceType();
                        if (StringUtils.isNotBlank(deviceType) && deviceType.startsWith("GB")) {
                            e.setDevicePort(thridConfig.getGbDevicePort());
                        } else {
                            e.setDevicePort(thridConfig.getNonGbDevicePort());
                        }
                    });
                }
                return CommonResponse.success(new PageRspDto<>(count, pageCount, result));
            } else {
                throw new ServiceException(ErrorCode.ERROR, response.getBody().getResponseDesc());
            }
        } catch (Exception ex) {
            log.error("调用findDeviceList接口异常,异常信息：{}", ex.getMessage());
            return CommonResponse.success(new PageRspDto<>(0, 0, result));
        }
    }

    private Integer getDeviceState(String state) {
        if ("DeviceOnline".equals(state)) {
            return DeviceStateEnum.ONLINE.getCode();
        } else {
            return DeviceStateEnum.OFFLINE.getCode();
        }
    }

    @GetMapping("/queryDeviceStatus")
    @ApiOperation("获取设备的在线数量以及总数")
    public CommonResponse<List<DeviceStatusRspDto>> queryDeviceStatus(){
        List<DeviceStatusRspDto> deviceStatusRspDto = this.monitorDeviceMapper.queryDeviceStatus();
        return CommonResponse.success(deviceStatusRspDto);
    }


    @GetMapping("/queryDeviceByArea")
    @ApiOperation("根据区域查询设备")
    public CommonResponse<List<DeviceCntByAreaRspDto>> queryDeviceByArea(){
        List<DicItem> district_type = this.dicItemMapper.queryDicItemList("district_type");
        List<DeviceCntByAreaRspDto> deviceCntByAreaRspDtos = this.monitorDeviceMapper.queryDeviceByArea();
        List<DeviceCntByAreaRspDto> retDtos = new ArrayList<>();
        Map<String, DeviceCntByAreaRspDto> collect = deviceCntByAreaRspDtos.stream()
                .collect(Collectors.toMap(DeviceCntByAreaRspDto::getAreaCode, it -> it));
        district_type.forEach(it->{
            if(!collect.containsKey(it.getItemCode())){
                DeviceCntByAreaRspDto dto = new DeviceCntByAreaRspDto();
                dto.setAreaCode(it.getItemCode());
                dto.setAreaName(it.getItemName());
                dto.setCnt(0);
                retDtos.add(dto);
            }else{
                DeviceCntByAreaRspDto dto = collect.get(it.getItemCode());
                dto.setAreaName(it.getItemName());
                retDtos.add(dto);
            }
        });

        return CommonResponse.success(retDtos);
    }

    @GetMapping("/queryAllResInfoForMap")
    @ApiOperation("查询地图中的所有的资源,包括设备，门禁和楼栋")
    public CommonResponse<List<ResInfoForMapRspDto>> queryAllResInfoForMap(@RequestParam(required = false) String category){
//        String category ="";
        List<ResInfoForMapRspDto> resInfoForMapRspDtos = this.monitorDeviceMapper.queryAllResInfoForMap(category);
        resInfoForMapRspDtos.forEach(it->{
            if(it.getDevState()==0){
                it.setDeviceState("online");
            }else{
                it.setDeviceState("offline");
            }
        });
        return CommonResponse.success(resInfoForMapRspDtos);
    }
}