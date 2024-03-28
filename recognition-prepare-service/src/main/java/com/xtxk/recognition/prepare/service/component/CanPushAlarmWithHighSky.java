package com.xtxk.recognition.prepare.service.component;

import cn.hutool.core.lang.hash.Hash;
import com.xtxk.recognition.prepare.service.component.annotation.CanPushAlarmProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@CanPushAlarmProcessor(EventCode = "gkpwsj",IsEnable = true)
@Slf4j
public class CanPushAlarmWithHighSky extends AbstractCanPushAlarm{
    @Value("${alg.gkpw.event:glpwsj}")
    private String eventCode;

    @Override
    public Boolean doCanPushAlarm(String data) {
        return true;
    }

    @Override
    public List<Integer> doGetPoints(String data) {
        return new ArrayList<>();
    }

    @Override
    public HashMap<String, Integer> doGetVioResource(String data, String resourceId,List<String> features) {
        HashMap<String,Integer> ret = new HashMap<>();
        ret.put(resourceId,3);//违规类型（1：违规车辆;2：违规人员3;3.违规楼栋）
        return ret;
       // return new HashMap<String,Integer>().put(resourceId,3);
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