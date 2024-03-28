package com.xtxk.recognition.prepare.service.component;

import cn.hutool.core.lang.hash.Hash;

import java.util.HashMap;
import java.util.List;

public interface ICanPushAlarm {
    Boolean canPushAlarm(String data,String bucketName,String actionKey);
    Boolean isSupport(String eventCode);
    List<Integer> getPoints(String data);

    HashMap<String,Integer> getVioResource(String data, String resourceId,List<String> features);

    HashMap<String,List<List<Integer>>> getCoordinate(String data);
}
