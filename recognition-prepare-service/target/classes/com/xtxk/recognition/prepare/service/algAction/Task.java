package com.xtxk.recognition.prepare.service.algAction;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.function.Predicate;

@Data
public class Task {
    private String name;
    private LocalDateTime start;
    private Predicate<AlgLoopModel> action;
    private Predicate<AlgLoopModel> notify;
    private AlgLoopModel model;
    //单位为秒
    private Integer interval;

    public void init(AlgLoopModel model,Predicate<AlgLoopModel> action,Predicate<AlgLoopModel> notify){
        this.interval = model.getDuration();
        this.action = action;
        this.notify = notify;
        this.start = LocalDateTime.now();
        this.model = model;
        this.name =this.model.getContext();
    }
}