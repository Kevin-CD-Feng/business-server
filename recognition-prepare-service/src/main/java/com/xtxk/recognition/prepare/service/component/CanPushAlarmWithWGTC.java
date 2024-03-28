package com.xtxk.recognition.prepare.service.component;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.xtxk.recognition.prepare.service.component.annotation.CanPushAlarmProcessor;
import com.xtxk.recognition.prepare.service.parser.AlgormParser;
import com.xtxk.recognition.prepare.service.parser.model.XCLRXREventResult;
import com.xtxk.recognition.prepare.service.parser.model.XCLRXRJsonEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

//机动车乱停乱放
@Component
@CanPushAlarmProcessor(EventCode = "jdcltlfsj",IsEnable = true)
@Slf4j
public class CanPushAlarmWithWGTC extends AbstractCanPushAlarm {

    @Value("${alg.wgtc.event:jdcltlfsj}")
    private String eventCode;

    @Value("${alg.wgtc.coff:0.5}")
    private String wgtc;

    @Override
    public Boolean doCanPushAlarm(String data) {
        try {
            XCLRXRJsonEntity parse = AlgormParser.parse(data, XCLRXRJsonEntity.class);
            if(parse.getData().getEvent_result()!=null||parse.getData().getEvent_result().size()>0) {
                List<XCLRXREventResult> collect = parse.getData().getEvent_result().stream().filter(it ->
                        it.getConf() >= Float.parseFloat(wgtc)
                                && it.getLabel().equals("vehicle")).collect(Collectors.toList());
                if(collect.size()>0){
                    return true;
                }
            }
        } catch (JsonProcessingException | NullPointerException e) {
            log.error("CanPushAlarmWithWGTC fires errors {}.",e.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public List<Integer> doGetPoints(String data) {
        List<Integer> points = new ArrayList<>();
        try {
            XCLRXRJsonEntity parse = AlgormParser.parse(data, XCLRXRJsonEntity.class);
            if(parse.getCode().equals(200)){
                List<XCLRXREventResult> dataItems = parse.getData().getEvent_result().stream().filter(it ->
                        it.getConf() >= Float.parseFloat(wgtc)
                                && it.getLabel().equals("vehicle")).collect(Collectors.toList());
                if(dataItems.size()>0){
                    dataItems.forEach(it->{
                        points.addAll(it.getBbox());
                    });
                }
            }
        } catch (JsonProcessingException e) {
            log.error("CanPushAlarmWithWGTC doGetPoints,data:{} 解析报错.",data);
        }
        return points;
    }

    @Override
    public HashMap<String, Integer> doGetVioResource(String data, String resourceId,List<String> features) {
        try {
            XCLRXRJsonEntity parse = AlgormParser.parse(data, XCLRXRJsonEntity.class);
            if(parse.getCode().equals(200)){
                List<XCLRXREventResult> dataItems = parse.getData().getEvent_result().stream().filter(it ->
                        it.getConf() >= Float.parseFloat(wgtc)
                                && it.getLabel().equals("vehicle")).collect(Collectors.toList());
                if(dataItems.size()>0){
                    HashMap<String,Integer> ret = new HashMap<>();
                    //ret.put(dataItems.get(0).getCarNo(),1);//违规类型（1：违规车辆;2：违规人员3;3.违规楼栋）
                    ret.put(dataItems.get(0).getPlate(),1);
                    return ret;
                }
            }
        } catch (JsonProcessingException e) {
            log.error("CanPushAlarmWithWGTC doGetPoints,data:{} 解析报错.",data);
        }
        return new HashMap<>();
    }

    @Override
    public void initProperties() {
        this.innerEventCode =this.eventCode;
    }

    @Override
    public HashMap<String,List<List<Integer>>> doGetCoordinate(String data) {
        return new HashMap<>();
    }
}