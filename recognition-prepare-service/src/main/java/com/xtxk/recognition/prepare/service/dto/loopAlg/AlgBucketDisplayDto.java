package com.xtxk.recognition.prepare.service.dto.loopAlg;

import com.xtxk.recognition.prepare.service.algAction.ActionTaskService;
import com.xtxk.recognition.prepare.service.algAction.AlgAction;
import com.xtxk.recognition.prepare.service.algAction.AlgLoopModel;
import com.xtxk.recognition.prepare.service.algAction.AlgModel;
import com.xtxk.recognition.prepare.service.manager.HttpManager;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;

@Data
public class AlgBucketDisplayDto {
    private List<AlgLoopModel> resources;
    private HashMap<String, AlgModel> actions; //algCode_eventCode_Order_resourceId
    private String bucketName;
    private AlgLoopModel currentLoop;
}