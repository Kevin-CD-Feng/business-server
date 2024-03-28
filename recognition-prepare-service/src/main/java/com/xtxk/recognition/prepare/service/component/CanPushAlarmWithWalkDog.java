package com.xtxk.recognition.prepare.service.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xtxk.recognition.prepare.service.component.annotation.CanPushAlarmProcessor;
import com.xtxk.recognition.prepare.service.dto.loopAlg.FeatureDto;
import com.xtxk.recognition.prepare.service.dto.loopAlg.SimilarityDto;
import com.xtxk.recognition.prepare.service.parser.AlgormParser;
import com.xtxk.recognition.prepare.service.parser.model.CrawlDataItem;
import com.xtxk.recognition.prepare.service.parser.model.CrawlResp;
import com.xtxk.recognition.prepare.service.parser.model.WalkDogDataItem;
import com.xtxk.recognition.prepare.service.parser.model.WalkDogResp;
import com.xtxk.recognition.prepare.service.svc.SimilarityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/*
* {
         "success": true,
         "data": [
              {
                 "bbox": [x,y,w,h], # 外接矩形框, 矩形左上点(x,y)，矩形宽高(w，h)
                 "conf":0.9,        # 检测置信度
                 "label":"dog"  #标签
              },
              {
 				 "bbox": [x,y,w,h], # 人员外接矩形框, 矩形左上点(x,y)，矩形宽高(w，h)
                 "conf":0.9,        # 人员检测置信度
                 "label":"person",  #标签
                 "face_bbox":[x,y,w,h], #人脸
                 "face_conf":0.8         #人脸
              }
             ],
              "params":{  #透传
		         "device_id":"",#由于有状态模型
		          "app_name":"",#透传字段
		          "timestamp":, #时间戳
		          "gaptime":0.001  #抽帧间隔时间

		       }
    }
*
* */

@Component
@CanPushAlarmProcessor(EventCode = "zgdlgsj",IsEnable = true)
@Slf4j
public class CanPushAlarmWithWalkDog extends AbstractCanPushAlarm {
    @Value("${alg.walkdog.coff:0.5}")
    private String walkDog;

    @Value("${alg.walkdog.event:zgdlgsj}")
    private String eventCode;

    @Autowired
    private SimilarityService similarityService;

    @Override
    public Boolean doCanPushAlarm(String data) {
        try {
            WalkDogResp parse = AlgormParser.parse(data, WalkDogResp.class);
            if(parse.getSuccess()){
                List<WalkDogDataItem> dataItems = parse.getData().stream()
                        .filter(it -> it.getConf() > Float.parseFloat(this.walkDog) && it.getLabel().equals("dog"))
                        .collect(Collectors.toList());
                if(dataItems.size()>0){
                    return true;
                }
            }
        } catch (JsonProcessingException e) {
            log.error("CanPushAlarmWithWalkDog doCanPushAlarm,data:{} 解析报错.",data);
            return false;
        }
        return false;
    }

    @Override
    public List<Integer> doGetPoints(String data) {
        List<Integer> points = new ArrayList<>();
        try {
            WalkDogResp parse = AlgormParser.parse(data, WalkDogResp.class);
            if(parse.getSuccess()){
                List<WalkDogDataItem> dataItems = parse.getData().stream()
                        .filter(it -> it.getConf() > Float.parseFloat(this.walkDog) && it.getLabel().equals("dog"))
                        .collect(Collectors.toList());
                if(dataItems.size()>0){
                    dataItems.forEach(it->{
                        points.addAll(it.getBbox());
                    });
                }

            }
        } catch (JsonProcessingException e) {
            log.error("CanPushAlarmWithWalkDog doGetPoints,data:{} 解析报错.",data);
        }

        return points;
    }

    @Override
    public HashMap<String, Integer> doGetVioResource(String data, String resourceId,List<String> features) {
        List<SimilarityDto> f = new ArrayList<>();
        features.forEach(it->{
            SimilarityDto targetInfo = this.similarityService.getTargetInfo(it);
            f.add(targetInfo);
        });
        Optional<SimilarityDto> first = f.stream().max(Comparator.comparing(SimilarityDto::getCoff));
        if(first.isPresent() && first.get().getPersonId()!=null){
           HashMap<String,Integer> ret = new HashMap<>();
           ret.put(String.valueOf(first.get().getPersonId()),2);//违规类型（1：违规车辆;2：违规人员3;3.违规楼栋）
           return ret;
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