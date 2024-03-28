package com.xtxk.recognition.prepare.service.component;

import com.xtxk.recognition.prepare.service.algAction.AlgAction;
import com.xtxk.recognition.prepare.service.algAction.AlgBucket;
import com.xtxk.recognition.prepare.service.algAction.AlgDispatcher;
import com.xtxk.recognition.prepare.service.algAction.AlgLoopModel;
import com.xtxk.recognition.prepare.service.component.annotation.CanPushAlarmProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
public abstract class AbstractCanPushAlarm implements ICanPushAlarm, ApplicationContextAware {

    protected String innerEventCode = "";
    protected Boolean isEnable = true;

    @Autowired
    private AlgDispatcher algDispatcher;

    @Override
    public Boolean canPushAlarm(String data, String bucketName, String resourceId) {
        return this.doCanPushAlarm(data);
//        canPushAlarm =true;
//        AtomicReference<Boolean> testFlag = new AtomicReference<>(false);
//        if(canPushAlarm){
//            HashMap<String, AlgBucket> buckets = this.algDispatcher.getBuckets();
//            if(buckets.containsKey(bucketName)){
//                AlgBucket bucket = buckets.get(bucketName);
//                AlgLoopModel loopModel = bucket.getCurrentLoop().peekFirst();
//                String actionKey = loopModel.getContext()+"_"+resourceId.trim();
//                if(bucket.getActions().containsKey(actionKey)){
//                    AlgAction action = bucket.getActions().get(actionKey);
//                    if(action!=null){
//                        testFlag.set(action.Test(""));
//                    }
//                }
//            }
//        }
//        return canPushAlarm & testFlag.get();
    }

    public abstract Boolean doCanPushAlarm(String data);

    public abstract List<Integer> doGetPoints(String data);

    public abstract HashMap<String, Integer> doGetVioResource(String data, String resourceId, List<String> features);

    public abstract void initProperties();

    public abstract HashMap<String,List<List<Integer>>> doGetCoordinate(String data);

    @Override
    public Boolean isSupport(String eventCode) {
        if (innerEventCode.equals(eventCode) && isEnable) {
            return true;
        }
        return false;
    }

    @Override
    public List<Integer> getPoints(String data) {
        return this.doGetPoints(data);
    }

    @Override
    public HashMap<String, Integer> getVioResource(String data, String resourceId, List<String> features) {
        return this.doGetVioResource(data, resourceId, features);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(CanPushAlarmProcessor.class);
        Set<Map.Entry<String, Object>> entrySet = beansWithAnnotation.entrySet();
        for (Map.Entry<String, Object> objectEntry : entrySet) {
            Class<?> clazz = objectEntry.getValue().getClass();
            CanPushAlarmProcessor clazzAnnotation = clazz.getAnnotation(CanPushAlarmProcessor.class);
            Object bean = applicationContext.getBean(clazz);
            if (bean instanceof ICanPushAlarm) {
                this.innerEventCode = clazzAnnotation.EventCode();
                this.isEnable = clazzAnnotation.IsEnable();
            }
        }
        this.initProperties();
    }

    @Override
    public HashMap<String,List<List<Integer>>> getCoordinate(String data) {
        return this.doGetCoordinate(data);
    }
}