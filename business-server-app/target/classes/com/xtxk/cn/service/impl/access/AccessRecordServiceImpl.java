package com.xtxk.cn.service.impl.access;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xtxk.cn.dto.accessRecord.*;
import com.xtxk.cn.service.access.AccessRecordService;
import com.xtxk.cn.utils.http.HttpResponse;
import com.xtxk.cn.utils.http.HutoolHttpClient;
import com.xtxk.cn.utils.json.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccessRecordServiceImpl implements AccessRecordService {
    @Value("${middleData.url}")
    private String url;

    @Value("${middleData.jwt}")
    private String jwt;

    @Value("${middleData.proxyIp}")
    private String proxyIp;

    @Value("${middleData.proxyPort}")
    private Integer proxyPort;

    @Value("${car.inUrl}")
    private String carInUrl;

    @Value("${car.outUrl}")
    private String carOutUrl;

    @Value("${car.pagesize}")
    private Integer pagesize;

    @Override
    public AccessPersonRecordResult getAccessPersonRecordResult() {
        HutoolHttpClient client = new HutoolHttpClient();
        AccessPersonRecordReqDto dto = new AccessPersonRecordReqDto();
        dto.setJwt(jwt);
        dto.setOperate("query");
        dto.setCustomKey("accessControlRecordService");
        dto.setArea("central");
        dto.setTimeQuantum("today");
        JSONObject jsonObject = JSONUtil.parseObj(dto);
        // 创建代理服务器对象
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, proxyPort));
        //办公网请求
//        HttpResponse httpResponse = client.postJson(url, jsonObject);
        //小区内网请求需添加代理
        HttpResponse httpResponse = client.postJson(url, jsonObject, null, proxy);
        List<AccessPersonRecordRespDto> list = JSONUtil.toList((JSONArray) httpResponse.getJsonResult().get("data"), AccessPersonRecordRespDto.class);
        AccessPersonRecordResult result = new AccessPersonRecordResult();
        if (list != null && list.size() > 0) {
            Map<String, List<AccessPersonRecordRespDto>> collect = list.stream().sorted(Comparator.comparing(AccessPersonRecordRespDto::getAccess_time)).collect(Collectors.groupingBy(AccessPersonRecordRespDto::getAccess_direction));
            result.setTotal((Integer) httpResponse.getJsonResult().get("totalSize"));
            result.setFlag((Boolean) httpResponse.getJsonResult().get("flag"));

            PersonOutItemList outItemList = new PersonOutItemList();
            PersonInItemList inItemList = new PersonInItemList();

            for (Map.Entry<String, List<AccessPersonRecordRespDto>> entry : collect.entrySet()) {
                if (entry.getKey().equals("出")) {
                    List<PersonTimeSlot> personTimeSlotList = new ArrayList<>();
                    List<AccessPersonRecordRespDto> outDataList = entry.getValue();
                    //按时间整点分类
                    Map<Object, List<AccessPersonRecordRespDto>> outCollect = outDataList.stream().collect(Collectors.groupingBy(item -> item.getAccess_time().getHour()));
                    for (Map.Entry<Object, List<AccessPersonRecordRespDto>> out : outCollect.entrySet()) {
                        PersonTimeSlot pts = new PersonTimeSlot();
                        pts.setTimeSlot(out.getKey().toString());
                        pts.setCurCount(out.getValue().size());
//                    pts.setPersons(out.getValue());
                        personTimeSlotList.add(pts);
                    }
                    outItemList.setPersonOutList(personTimeSlotList);
                    outItemList.setOutCount(entry.getValue().size());
                    result.setPersonOutItemList(outItemList);
                }
                if (entry.getKey().equals("入")) {
                    List<PersonTimeSlot> personTimeSlotList = new ArrayList<>();
                    List<AccessPersonRecordRespDto> inDataList = entry.getValue();
                    //按时间整点分类
                    Map<Object, List<AccessPersonRecordRespDto>> outCollect = inDataList.stream().collect(Collectors.groupingBy(item -> item.getAccess_time().getHour()));
                    for (Map.Entry<Object, List<AccessPersonRecordRespDto>> out : outCollect.entrySet()) {
                        PersonTimeSlot pts = new PersonTimeSlot();
                        pts.setTimeSlot(out.getKey().toString());
                        pts.setCurCount(out.getValue().size());
//                    pts.setPersons(out.getValue());
                        personTimeSlotList.add(pts);
                    }
                    inItemList.setPersonInList(personTimeSlotList);
                    inItemList.setInCount(entry.getValue().size());
                    result.setPersonInItemList(inItemList);
                }
            }
        }

        return result;
    }

    @Override
    public AccessCarInRecordResult getAccessCarInRecordResult(String date) {
        HutoolHttpClient client = new HutoolHttpClient();
        AccessCarRecordReqDto dto = new AccessCarRecordReqDto();
        dto.setPagesize(pagesize);
        dto.setWhere(String.format(" InTime between '%s 00:00:00.000' and '%s 23:59:59.000' ", date, date));
        JSONObject jsonObject = JSONUtil.parseObj(dto);
        HttpResponse response = client.postJson(carInUrl, jsonObject);
        AccessCarInRecordResult result = new AccessCarInRecordResult();
        AccessCarPageAttriDto attri = response.getJsonResult().get("PageAttri", AccessCarPageAttriDto.class);
        result.setTotal(attri.getTotalCount());
        List<AccessCarInRecordRespDto> records = JSONUtil.toList((JSONArray) response.getJsonResult().get("Records"), AccessCarInRecordRespDto.class);

        if (records != null && records.size() > 0) {
            Map<Object, List<AccessCarInRecordRespDto>> collect = records.stream().sorted(Comparator.comparing(AccessCarInRecordRespDto::getInTime)).collect(Collectors.groupingBy(AccessCarInRecordRespDto::getInType));
            for (Map.Entry<Object, List<AccessCarInRecordRespDto>> entry : collect.entrySet()) {
                List<CarTimeSlot> carTimeSlotList = new ArrayList<>();
                List<AccessCarInRecordRespDto> inRecordRespDtoList = entry.getValue();
                //按时间整点分类
                Map<Object, List<AccessCarInRecordRespDto>> inCollect = inRecordRespDtoList.stream().collect(Collectors.groupingBy(item -> item.getInTime().getHour()));
                for (Map.Entry<Object, List<AccessCarInRecordRespDto>> in : inCollect.entrySet()) {
                    CarTimeSlot cts = new CarTimeSlot();
                    cts.setTimeSlot(in.getKey().toString());
                    cts.setCurCount(in.getValue().size());
                    carTimeSlotList.add(cts);
                }

                result.setCarInList(carTimeSlotList);
            }
        }

        return result;
    }

    @Override
    public AccessCarOutRecordResult getAccessCarOutRecordResult(String date) {
        HutoolHttpClient client = new HutoolHttpClient();
        AccessCarRecordReqDto dto = new AccessCarRecordReqDto();
        dto.setPagesize(pagesize);
        dto.setWhere(String.format(" OutTime between '%s 00:00:00.000' and '%s 23:59:59.000' ", date, date));
        JSONObject jsonObject = JSONUtil.parseObj(dto);
        HttpResponse response = client.postJson(carOutUrl, jsonObject);
        AccessCarOutRecordResult result = new AccessCarOutRecordResult();
        AccessCarPageAttriDto attri = response.getJsonResult().get("PageAttri", AccessCarPageAttriDto.class);
        result.setTotal(attri.getTotalCount());
        List<AccessCarOutRecordRespDto> records = JSONUtil.toList((JSONArray) response.getJsonResult().get("Records"), AccessCarOutRecordRespDto.class);

        if (records != null && records.size() > 0) {
            Map<Object, List<AccessCarOutRecordRespDto>> collect = records.stream().sorted(Comparator.comparing(AccessCarOutRecordRespDto::getOutTime)).collect(Collectors.groupingBy(AccessCarOutRecordRespDto::getOutType));
            for (Map.Entry<Object, List<AccessCarOutRecordRespDto>> entry : collect.entrySet()) {
                List<CarTimeSlot> carTimeSlotList = new ArrayList<>();
                List<AccessCarOutRecordRespDto> outRecordRespDtoList = entry.getValue();
                //按时间整点分类
                Map<Object, List<AccessCarOutRecordRespDto>> outCollect = outRecordRespDtoList.stream().collect(Collectors.groupingBy(item -> item.getOutTime().getHour()));
                for (Map.Entry<Object, List<AccessCarOutRecordRespDto>> out : outCollect.entrySet()) {
                    CarTimeSlot cts = new CarTimeSlot();
                    cts.setTimeSlot(out.getKey().toString());
                    cts.setCurCount(out.getValue().size());
                    carTimeSlotList.add(cts);
                }

                result.setCarOutList(carTimeSlotList);
            }
        }

        return result;
    }
}
