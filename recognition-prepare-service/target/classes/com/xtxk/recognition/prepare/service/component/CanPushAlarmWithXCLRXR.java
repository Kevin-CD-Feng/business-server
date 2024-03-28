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


//机动车不礼让行人
@Component
@CanPushAlarmProcessor(EventCode = "jdcblrxrsj", IsEnable = true)
@Slf4j
public class CanPushAlarmWithXCLRXR extends AbstractCanPushAlarm {
    @Value("${alg.xclrxr.pcoff:0.5}")
    private String personConf;

    @Value("${alg.xclrxr.ccoff:0.5}")
    private String carConf;

    @Value("${alg.xclrxr.event:jdcblrxrsj}")
    private String eventCode;

    @Override
    public Boolean doCanPushAlarm(String data) {
        try {
            XCLRXRJsonEntity parse = AlgormParser.parse(data, XCLRXRJsonEntity.class);
            if (parse.getData().getEvent_result() != null || parse.getData().getEvent_result().size() > 0) {
                List<XCLRXREventResult> collect = parse.getData().getEvent_result().stream().filter(it ->
                        it.getViolation().equals("yes")
                                && it.getPerson_conf() >= Float.parseFloat(personConf)
                                && it.getCar_conf() >= Float.parseFloat(carConf)).collect(Collectors.toList());
                if (collect.size() > 0) {
                    return true;
                }
            }
        } catch (JsonProcessingException | NullPointerException e) {
            log.error("CanPushAlarmWithXCLRXR解析报错.");
            return false;
        }
        return false;
    }

    @Override
    public List<Integer> doGetPoints(String data) {
        List<Integer> points = new ArrayList<>();
        XCLRXRJsonEntity jsonEntity = null;
        try {
            jsonEntity = AlgormParser.parse(data, XCLRXRJsonEntity.class);
        } catch (JsonProcessingException e) {
            //e.printStackTrace();
            log.error("{} 事件 解析报错.{}", this.eventCode, data);
            return points;
        }

        try {
            if (jsonEntity.getData().getEvent_result() != null || jsonEntity.getData().getEvent_result().size() > 0) {
                jsonEntity.getData().getEvent_result().forEach(it -> {
                    if (it.getViolation().equals("yes") && it.getPerson_conf() >= Float.parseFloat(personConf)
                            && it.getCar_conf() >= Float.parseFloat(carConf)) {
                        points.addAll(it.getCar_bbox());
                        points.addAll(it.getPerson_bbox());

                    }
                });
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            log.error("{} 事件 解析报错2.{}", this.eventCode, data);
            return points;
        }
        return points;
    }

    @Override
    public HashMap<String, Integer> doGetVioResource(String data, String resourceId, List<String> features) {
        return new HashMap<>();
    }

    @Override
    public void initProperties() {
        this.innerEventCode = this.eventCode;
    }

    @Override
    public HashMap<String, List<List<Integer>>> doGetCoordinate(String data) {
        HashMap<String, List<List<Integer>>> coordinates = new HashMap<>();
        XCLRXRJsonEntity jsonEntity = null;
        try {
            jsonEntity = AlgormParser.parse(data, XCLRXRJsonEntity.class);
        } catch (JsonProcessingException e) {
            //e.printStackTrace();
            log.error("{} 事件 解析报错.{}", this.eventCode, data);
            return coordinates;
        }

        try {
            if (jsonEntity.getData().getEvent_result() != null || jsonEntity.getData().getEvent_result().size() > 0) {
                jsonEntity.getData().getEvent_result().forEach(it -> {
                    if (it.getViolation().equals("yes") && it.getPerson_conf() >= Float.parseFloat(personConf)
                            && it.getCar_conf() >= Float.parseFloat(carConf)) {
                        //人车移动轨迹放入map
                        coordinates.put("car", it.getCar_points());
                        coordinates.put("person", it.getPerson_points());

                    }
                });
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            log.error("{} 事件 解析报错2.{}", this.eventCode, data);
            return coordinates;
        }
        return coordinates;
    }
}