package com.xtxk.recognition.prepare.service.algAction;

import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Data
public class AlgLoopModel implements Serializable {
    private ConcurrentLinkedQueue<AlgModel> models;
    private Integer duration;
    private String context;//algCode_eventCode_Order其实就是Bucket中一系列轮询资源AlgModel的抽象
    private Integer order;

    public void init(Integer duration, String context, List<AlgModel> models){
        this.duration =duration;
        this.context = context;
        models.forEach(it->{
            it.setOrder(this.order);
        });
        this.models = new ConcurrentLinkedQueue<>(models);
    }

    public Boolean isEnable(){
        return this.models.stream().anyMatch(it -> Strings.isNotEmpty(it.getWebCoordinate()));
    }
}