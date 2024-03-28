package com.xtxk.recognition.prepare.service.mock;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.PathUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xtxk.recognition.prepare.service.dto.alarmDefine.CanPushAlarmReqDto;
import com.xtxk.recognition.prepare.service.dto.alarmDefine.PushAlarmInfoReqDto;
import com.xtxk.recognition.prepare.service.entity.DicItem;
import com.xtxk.recognition.prepare.service.manager.HttpManager;
import com.xtxk.recognition.prepare.service.mapper.DicItemMapper;
import com.xtxk.recognition.prepare.service.svc.AlarmDefineService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.file.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class AlarmFileReader implements CommandLineRunner {
    private final static String alarmInfoFileName="alarm";

    @Autowired
    private DicItemMapper dicItemMapper;

    @Autowired
    private AlarmDefineService alarmDefineService;

    @Autowired
    private HttpManager httpManager;

    @Value("${scheduling.alarmWithFace}")
    private String alarmWithFace;

    @Value("${scheduling.mockEnable:yes}")
    private String mockEnable;

    private HashMap<String,String> getFiles(){
        String dir = this.getCurrentDir();
        String[] list = new File(dir).list();
        assert list != null;
        List<String> collect = Stream.of(list).collect(Collectors.toList());
        HashMap<String,String> ret = new HashMap<>();
        collect.forEach(it->{
            String s = StrUtil.subBefore(it, ".", true);
            ret.put(s,dir+"/"+it);
        });
        return ret;
    }

    private String getCurrentDir(){
        String cur= System.getProperty("user.dir")+"/json";
        log.info("current dir:{}",cur);
        return cur;
    }

    private PushAlarmInfoReqDto getRequestTemplate(Map<String,String> files){
        String file = files.get(alarmInfoFileName);
        String fullName =file;
        String content = FileUtil.readString(fullName, Charset.defaultCharset());
        PushAlarmInfoReqDto dto = JSONUtil.toBean(content, PushAlarmInfoReqDto.class);
        return dto;
    }

    private String getData(String file){
        String fullName = file;
        String content = FileUtil.readString(fullName, Charset.defaultCharset());
        return content;
    }

    private Map<String,DicItem> getAlarmEventCode(){
        List<DicItem> event_type = this.dicItemMapper.queryDicItemListByDicCode("alarm_event");
        return event_type.stream().collect(Collectors.toMap(DicItem::getItemCode, it -> it));
    }

    public void pulseSend(){
        if(this.mockEnable.equals("yes")){
            Thread thread = new Thread(()->{
                while (true){
                    try {
                        sendByDuration();
                        Thread.sleep(30*1000*2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    private boolean isFaceWith(String eventCode){
        String[] split = this.alarmWithFace.split(",");
        Optional<String> first = Stream.of(split).filter(it -> it.equals(eventCode)).findFirst();
        if(first.isPresent()){
            return true;
        }
        return false;
    }
    public void sendByDuration() {

        log.info("pulse send.");
        HashMap<String, String> files = this.getFiles();
        Map<String, DicItem> alarmEventCode = this.getAlarmEventCode();
        //FileUtil.readString()
        PushAlarmInfoReqDto requestTemplate = getRequestTemplate(files);
        files.forEach((k,v)->{
            if(alarmEventCode.containsKey(k)){
                //发送对应的事件
                String filename = files.get(k);
                String data = getData(filename);
                requestTemplate.setData(data);
                requestTemplate.setEventCode(k);
                String catchTimeii = String.valueOf(System.currentTimeMillis());
                requestTemplate.setCatchTime(catchTimeii);
                if(isFaceWith(k)){
                    String feature = this.getFeature();
                    requestTemplate.setCoff(Stream.of(feature).collect(Collectors.toList()));
                }
                try {
                    sendAlarmInfo(requestTemplate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getFeature(){
        String featurePath =this.getCurrentDir()+"/feature.json";
        String content = FileUtil.readString(featurePath, Charset.defaultCharset());
        return content;
    }

    private void sendAlarmInfo(PushAlarmInfoReqDto pushAlarmInfoReqDto) throws IOException {

        //画框
        this.alarmDefineService.dealAlarmInfo(pushAlarmInfoReqDto);
        //这里处理一下违规对象
        CanPushAlarmReqDto request = new CanPushAlarmReqDto();
        request.setData(pushAlarmInfoReqDto.getData());
        request.setResourceId(pushAlarmInfoReqDto.getResourceId());
        request.setEventCode(pushAlarmInfoReqDto.getEventCode());
        request.setFeatures(pushAlarmInfoReqDto.getCoff());
        HashMap<String, Integer> vioResource = this.alarmDefineService.getVioResource(request);
        //推送到业务服务处理告警信息
        JSONObject jsonReq = JSONUtil.createObj()
                .putOpt("resourceId", pushAlarmInfoReqDto.getResourceId())
                .putOpt("eventCode", pushAlarmInfoReqDto.getEventCode())
                .putOpt("roi", pushAlarmInfoReqDto.getRoi())
                .putOpt("catchTime", pushAlarmInfoReqDto.getCatchTime())
                .putOpt("path", pushAlarmInfoReqDto.getPath())
                .putOpt("data",JSONUtil.toJsonStr(vioResource));
        log.info("mock发送业务系统告警:{}",JSONUtil.toJsonStr(jsonReq));
        this.httpManager.notifyAlarmInfo(jsonReq);
    }

    @Override
    public void run(String... args) throws Exception {
        this.pulseSend();
    }
}