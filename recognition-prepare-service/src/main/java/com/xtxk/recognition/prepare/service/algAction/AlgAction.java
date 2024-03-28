package com.xtxk.recognition.prepare.service.algAction;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xtxk.recognition.prepare.service.dto.AlarmPolicyConfiguration.WebCoordinateDto;
import com.xtxk.recognition.prepare.service.manager.HttpManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AlgAction implements IAlgAction {

    public AlgModel getAlgModel() {
        return algModel;
    }

    private final AlgModel algModel;

    private final HttpManager httpManager;

    private Date firstDate;

    private Boolean hasTest;

    private final Integer firstMark;

    private final Integer inteval;

    public AlgAction(AlgModel model,HttpManager manager){
        this.algModel = model;
        this.httpManager =manager;
        this.firstMark = this.algModel.getDuration();
        this.inteval = this.algModel.getInterval();
    }

    @Override
    public void start() {
        if(Strings.isNotEmpty(this.algModel.getWebCoordinate())){
            log.debug("AlgAction"+":"+this.getName()+":"+"start."+"  interval:"+this.inteval+"       "+this.algModel.getResourceName());
            this.identityOpen(this.algModel.getWebCoordinate());
//            this.startRecordUrl();
        }
    }

    @Override
    public void stop() {
        log.debug("AlgAction"+":"+this.getName()+":"+"stop."+this.algModel.getResourceName());
        this.identityClose();
//        this.stopRecordUrl();
    }

    //只有真正告警的时候才推送
    @Override
    public boolean Test(String resourceId) {
        if(firstDate==null){
            this.firstDate = new Date();
            this.hasTest =false;
        }
        log.debug(this.getName()+" Test. firstMark:"+firstMark);
        long between = DateUtil.between(this.firstDate, new Date(), DateUnit.SECOND);
        log.debug(this.getName()+" Test. firstMark:"+firstMark+" between:"+between);
        if(between>=firstMark&&!hasTest){ //超过检测时长，且没有推送，标记推送，开始时间复位
            this.hasTest =true;//标记已经推送过了
            this.firstDate = new Date();
            log.debug(this.getName()+":"+"push");
            return true;
        }else if(between>=inteval&&hasTest){ //上一次已经推送了告警，间隔interval恢复配置
            this.hasTest=false;
            this.firstDate =new Date();
            log.debug(this.getName()+" Inteval.");
        }
        return false;
    }

    private void startRecordUrl(){
        JSONObject jsonReq = JSONUtil.createObj()
                .putOpt("resourceId", this.algModel.getResourceId())
                .putOpt("resourceName", this.algModel.getResourceName())
                .putOpt("resourceSipId",this.algModel.getResourceSipId());
        log.info("startRecordUrl.data:{}",JSONUtil.toJsonStr(jsonReq));
        httpManager.startRecordUrl(jsonReq);
    }

    private void  stopRecordUrl(){
        JSONObject jsonReq = JSONUtil.createObj()
                .putOpt("resourceId", this.algModel.getResourceId());
        log.info("stopRecordUrl.data:{}",JSONUtil.toJsonStr(jsonReq));
        httpManager.stopRecordUrl(jsonReq);
    }

    private void identityClose(){
        JSONObject jsonReq = JSONUtil.createObj()
                .putOpt("algorithmCode", this.algModel.getAlgCode())
                .putOpt("resourceId", this.algModel.getResourceId());
        httpManager.identifyClose(jsonReq);
    }

    private void identityOpen(String webCoordinate){
        JSONArray objects = JSONUtil.parseArray(webCoordinate);
        List<WebCoordinateDto> list = JSONUtil.toList(objects, WebCoordinateDto.class);
        Integer width = null;
        Integer height = null;
        List<Integer[]> roiList = new ArrayList<>();
        List<List<BigDecimal>> pointResult = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<BigDecimal> point = new ArrayList();
            width = list.get(i).getWidth();
            height = list.get(i).getHeight();
            Integer widthLength = list.get(i).getWidth();
            Integer heigthLength = list.get(i).getHeight();
            if (widthLength == 0 && heigthLength == 0) {
                continue;
            }
            Integer x1 = Optional.ofNullable(list.get(i).getRealX()).orElse(0);
            Integer y1 = Optional.ofNullable(list.get(i).getRealY()).orElse(0);
            Integer x2 = x1 + widthLength;
            Integer y2 = Optional.ofNullable(list.get(i).getRealY()).orElse(0);
            Integer x3 = x1 + widthLength;
            Integer y3 = y1 + heigthLength;
            Integer x4 = Optional.ofNullable(list.get(i).getRealX()).orElse(0);
            Integer y4 = y1 + heigthLength;
            Integer[] tt = {x1, y1, x2, y2, x3, y3, x4, y4};
            roiList.add(tt);
            List<List<BigDecimal>> pointData = list.get(i).getPoint();
            if (pointData != null && pointData.size() > 0) {
                for (List<BigDecimal> d : pointData) {
                    for (BigDecimal v : d) {
                        point.add(v);
                    }
                }
            }
            pointResult.add(point);
        }
        //Integer[][] integers = roiList.toArray(new Integer[][]{});
        JSONObject jsonObject = JSONUtil.createObj();
        if (pointResult.size() > 0) {
            jsonObject.putOpt("width", width).putOpt("height", height).putOpt("roi", pointResult);
        }

        Float frameInterval = this.algModel.getFrameInterval();
        if(frameInterval==null){
            frameInterval =1F;
        }
        JSONObject jsonReq = JSONUtil.createObj()
                .putOpt("algorithmCode", this.algModel.getAlgCode())
                .putOpt("period", frameInterval*1000) //这个值表示的是多少秒采集一帧
                .putOpt("eventCode", this.algModel.getEventCode())
                .putOpt("resourceId", this.algModel.getResourceId())
                .putOpt("resourceName", this.algModel.getResourceName())
                .putOpt("isEnableFace",this.algModel.getIsEnableFace())
                ;
        if(this.algModel.getInterval()!=null && this.algModel.getInterval()!=0){
            jsonReq.putOpt("interval",this.algModel.getInterval()); //该算法成功告警后，间隔多少秒停止采集。
        }
        if(this.algModel.getDuration()!=null && this.algModel.getDuration()!=0){
            Float aFloat = Float.valueOf(this.algModel.getDuration());
            jsonReq.putOpt("duration",aFloat/1000);//表示该算法持续多久，目前止违规停车有这个值
        }
        if (jsonObject.size() > 0) {
            jsonReq.putOpt("roi", jsonObject.toString());
        }
        log.info("开启算法:{}",JSONUtil.toJsonStr(jsonReq));
        httpManager.identifyOpen(jsonReq);
    }

    public String getName(){
        String[] splits = {this.algModel.getAlgCode().trim(),
                this.algModel.getEventCode().trim(),
                String.valueOf(this.algModel.getOrder()),this.algModel.getResourceId().trim()};
        return String.join("_",splits);
    }
}