package com.xtxk.recognition.prepare.service.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xtxk.recognition.prepare.service.component.annotation.CanPushAlarmProcessor;
import com.xtxk.recognition.prepare.service.dto.loopAlg.SimilarityDto;
import com.xtxk.recognition.prepare.service.parser.AlgormParser;
import com.xtxk.recognition.prepare.service.parser.model.CrawlDataItem;
import com.xtxk.recognition.prepare.service.parser.model.CrawlResp;
import com.xtxk.recognition.prepare.service.svc.SimilarityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;
/*
*  {
         "success": true,
         "data": [
             {
                 "person_bbox": [x,y,w,h], # 人员外接矩形框, 矩形左上点(x,y)，矩形宽高(w，h)
                 "person_conf":0.9,        # 人员检测置信度
                 "face_bbox":[x,y,w,h],    # 人脸外接矩形框，未检测出人脸时为[]
                 "face_conf":0.8           # 人脸检测置信度
              },
             ]
    }
*
* */

@Component
@CanPushAlarmProcessor(EventCode = "wgfysj",IsEnable = true)
@Slf4j
public class CanPushAlarmWithCrawl extends AbstractCanPushAlarm{

    @Value("${alg.craw.coff:0.5}")
    private String crawlCoff;
    @Value("${alg.craw.event:wgfysj}")
    private String eventCode;

    @Autowired
    private SimilarityService similarityService;

    @Override
    public Boolean doCanPushAlarm(String data) {
        try {
            CrawlResp parse = AlgormParser.parse(data, CrawlResp.class);
            if(parse.getSuccess()){
                List<CrawlDataItem> dataItems = parse.getData().stream()
                        .filter(it -> it.getPersonConf() > Float.parseFloat(this.crawlCoff))
                        .collect(Collectors.toList());
                if(dataItems.size()>0){
                    return true;
                }
            }
        } catch (JsonProcessingException | NullPointerException e) {
            log.error("CanPushAlarmWithCrawl doCanPushAlarm,data:{} 解析报错.",data);
            return false;
        }
        return false;
    }

    @Override
    public List<Integer> doGetPoints(String data) {
        List<Integer> points = new ArrayList<>();
        try {
            CrawlResp parse = AlgormParser.parse(data, CrawlResp.class);
            if(parse.getSuccess()){
                List<CrawlDataItem> dataItems = parse.getData().stream()
                        .filter(it -> it.getPersonConf() > Float.parseFloat(this.crawlCoff))
                        .collect(Collectors.toList());
                if(dataItems.size()>0){
                   dataItems.forEach(it->{
                       Optional<List<Integer>> faceBbox = Optional.ofNullable(it.getFaceBbox());
                       Optional<List<Integer>> personBbox = Optional.ofNullable(it.getPersonBbox());
                       points.addAll(faceBbox.orElse(new ArrayList<>()));
                       points.addAll(personBbox.orElse(new ArrayList<>()));
                   });
                }
            }
        } catch (JsonProcessingException e) {
            log.error("CanPushAlarmWithCrawl doGetPoints,data:{} 解析报错.",data);
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
            ret.put(String.valueOf(first.get().getPersonId()),2);//违规类型（1：违规车辆;2：违规人员;3.违规楼栋）
            return ret;
        }
        return new HashMap<>();

    }

    @Override
    public void initProperties() {
        this.innerEventCode = this.eventCode;
    }

    @Override
    public HashMap<String,List<List<Integer>>> doGetCoordinate(String data) {
        return new HashMap<>();
    }
}