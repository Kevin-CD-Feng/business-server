package com.xtxk.recognition.prepare.service.algAction;

import cn.hutool.core.lang.hash.Hash;
import cn.hutool.core.util.StrUtil;
import com.xtxk.recognition.prepare.service.dto.loopAlg.AlgLoopModelByConditionDto;
import com.xtxk.recognition.prepare.service.dto.loopAlg.LoopAlgResVo;
import com.xtxk.recognition.prepare.service.entity.AlarmDefine;
import com.xtxk.recognition.prepare.service.entity.AlarmPolicyConfiguration;
import com.xtxk.recognition.prepare.service.entity.DicItem;
import com.xtxk.recognition.prepare.service.entity.EvenalgorithmicBinding;
import com.xtxk.recognition.prepare.service.manager.HttpManager;
import com.xtxk.recognition.prepare.service.mapper.*;
import com.xtxk.recognition.prepare.service.svc.AlgDispatcherService;
import io.minio.messages.Bucket;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Component
public class AlgDispatcher implements CommandLineRunner {
    @Autowired
    private HttpManager httpManager;

    @Autowired
    private AlgDispatcherService algDispatcherService;

    @Autowired
    private EvenalgorithmicBindingMapper evenalgorithmicBindingMapper;

    @Autowired
    private ActionTaskService actionTaskService;

    private HashMap<String,AlgBucket> buckets;

    public HashMap<String,AlgBucket> getBuckets() {
        return buckets;
    }

    @Override
    public void run(String... args) throws Exception {
        this.buckets = new HashMap<>();
        HashMap<String, List<AlgLoopModel>> line = this.algDispatcherService.queryAlgLoopModelByCondition(null, null);
        line.forEach((k,v)->{
            AlgBucket bucket = new AlgBucket();
            bucket.init(this.actionTaskService,this.httpManager,v);
            bucket.setBucketName(k);
            this.buckets.put(k,bucket);
        });
        //将buckets其他的补充进去
        List<EvenalgorithmicBinding> dicItems = this.evenalgorithmicBindingMapper.queryEventAlgBindingAll();
        dicItems.forEach(it->{
            String bucketName = it.getAlgorithmicNameCode()+"_"+it.getEventCode();
            if(!this.buckets.containsKey(bucketName)){
                AlgBucket bucket = new AlgBucket();
                bucket.init(this.actionTaskService,this.httpManager, new ArrayList<>());
                bucket.setBucketName(bucketName);
                this.buckets.put(bucketName,bucket);
            }
        });


        this.buckets.values().forEach(AlgBucket::print);
        this.buckets.values().forEach(AlgBucket::start);
    }

    //新增算法绑定的时候，动态添加buckets
    public void refreshBuckets(){
        //将buckets其他的补充进去
        List<EvenalgorithmicBinding> dicItems = this.evenalgorithmicBindingMapper.queryEventAlgBindingAll();
        dicItems.forEach(it->{
            String bucketName = it.getAlgorithmicNameCode()+"_"+it.getEventCode();
            if(!this.buckets.containsKey(bucketName)){
                AlgBucket bucket = new AlgBucket();
                bucket.init(this.actionTaskService,this.httpManager, new ArrayList<>());
                bucket.setBucketName(bucketName);
                this.buckets.put(bucketName,bucket);
            }
        });
    }
}