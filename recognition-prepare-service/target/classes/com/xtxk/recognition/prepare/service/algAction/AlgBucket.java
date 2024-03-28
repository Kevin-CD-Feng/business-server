package com.xtxk.recognition.prepare.service.algAction;

import com.xtxk.recognition.prepare.service.manager.HttpManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Data
@Slf4j
public class AlgBucket {
    private ConcurrentLinkedDeque<AlgLoopModel> resources;
    private ConcurrentHashMap<String,AlgAction> actions; //algCode_eventCode_Order_resourceId
    private HttpManager httpManager;
    //一个值，指示内存数据正在更新，暂停所有的任务和更新
    private volatile Boolean isUpdating;
    private ExecutorService pollService;
    private ActionTaskService actionTaskService;
    private String bucketName;

    private ConcurrentLinkedDeque<AlgLoopModel> currentLoop;

    public void init(ActionTaskService service,HttpManager httpManager, List<AlgLoopModel> models){
        this.resources =new ConcurrentLinkedDeque<>(models);
        this.actionTaskService =service;
        this.httpManager = httpManager;
        this.actions = new ConcurrentHashMap<>();
        this.currentLoop = new ConcurrentLinkedDeque<>();
    }

    //删除algLoopModel
    public void removeAlgLoopModel(String loopKey){
        this.isUpdating=true;
        this.resources.removeIf(it -> it.getContext().equals(loopKey));
        AlgLoopModel current = this.currentLoop.peekFirst();
        if(current!=null && current.getContext().equals(loopKey)){
            this.actionTaskService.stop(loopKey);
            this.actions.values().forEach(AlgAction::stop);
            this.actions.clear();
            this.currentLoop.clear();
        }
        this.isUpdating=false;
    }

    //来自全量的更新
    public void updateAlgLoopModel(AlgLoopModel loopModel) {
        //处理正在运行的
        this.isUpdating=true;
        boolean isHitCurrent = false;
        AlgLoopModel current = this.currentLoop.peekFirst();
        if (current != null && current.getContext().equals(loopModel.getContext())) {
            isHitCurrent = true;
        }
        if (isHitCurrent) {
            log.debug("current:" + current.getContext());

            String contextKey = loopModel.getContext();
            Map<String, AlgModel> sourceAlgModelMap = loopModel.getModels()
                    .stream()
                    .collect(Collectors.toMap(it -> {return contextKey + "_" + it.getResourceId().trim();}, it -> it));

            //全量的设备
            Set<String> sourceSet = sourceAlgModelMap.keySet();
            Set<String> runSet = new HashSet<>();

            //获取正在运行的设备
            this.actions.forEach((key, v) -> {runSet.add(key);});
            //需要添加AlgModel
            Set<String> addedSet = new HashSet<>(sourceSet);
            addedSet.removeAll(runSet);
            //需要删除的AlgModel
            Set<String> delSet = new HashSet<>(runSet);
            delSet.removeAll(sourceSet);
            //需要更新AlgModel
            loopModel.getModels().forEach(item -> {
                String actionKey = contextKey + "_" + item.getResourceId().trim();
                if (this.actions.containsKey(actionKey)) {
                    AlgAction action = this.actions.get(actionKey);
                    action.stop();
                    this.actions.remove(actionKey);
                    AlgAction actionU = new AlgAction(item, httpManager);
                    String actionKeyU = actionU.getName();
                    this.actions.put(actionKeyU, actionU);
                    actionU.start();
                }
            });
            //删除action
            delSet.forEach(key -> {
                AlgAction action = this.actions.get(key);
                action.stop();
                this.actions.remove(key);
            });
            //添加action
            addedSet.forEach(key -> {
                AlgModel model = sourceAlgModelMap.get(key);
                AlgAction actionU = new AlgAction(model, httpManager);
                String actionKeyU = actionU.getName();
                this.actions.put(actionKeyU, actionU);
                actionU.start();
            });

            //更新resources
            this.resources.pollLast();
            this.resources.add(loopModel);
            //更新currentLoop
            if(this.actions.size()>0){
                this.currentLoop.pop();
                this.currentLoop.add(loopModel);
            }else{
                this.currentLoop.clear();
            }

        } else {
            this.resources.removeIf(it -> it.getContext().equals(loopModel.getContext()));
            this.resources.add(loopModel);
        }

        //更新后，前置排序
        this.sorted();
        this.isUpdating=false;
    }

    //增量更新
    public void updateAlgLoopModelByResId(AlgLoopModel loopModel){
        //处理正在运行的
        this.isUpdating=true;
        Boolean isHitCurrent = false;
        AlgLoopModel current = this.currentLoop.peekFirst();
        if (current != null && current.getContext().equals(loopModel.getContext())) {
            isHitCurrent = true;
        }
        if (isHitCurrent) {
            log.debug("current:" + current.getContext());

            String contextKey = loopModel.getContext();
            //需要更新AlgModel
            loopModel.getModels().forEach(item -> {
                String actionKey = contextKey + "_" + item.getResourceId().trim();
                //更新actions.
                if (this.actions.containsKey(actionKey)) {
                    AlgAction action = this.actions.get(actionKey);
                    action.stop();
                    this.actions.remove(actionKey);
                }
                AlgAction actionU = new AlgAction(item, httpManager);
                String actionKeyU = actionU.getName();
                this.actions.put(actionKeyU, actionU);
                actionU.start();
                //更新currentLoop
                AlgLoopModel loopModel1 = this.currentLoop.peekFirst();
                if(loopModel1!=null){
                    loopModel1.getModels().removeIf(it->it.getName().equals(actionKey));
                    loopModel1.getModels().add(item);
                }
                //更新resources
                this.resources.forEach(it->{
                    if(it.getContext().equals(contextKey)){
                        String actionKey1 = contextKey + "_" + item.getResourceId().trim();
                        it.getModels().removeIf(ps->ps.getName().equals(actionKey1));
                        it.getModels().add(item);
                    }
                });
            });

        } else {
              String contextKey = loopModel.getContext();
            Map<String, AlgLoopModel> resMap = this.resources.stream().collect(Collectors.toMap(AlgLoopModel::getContext, it -> it));
            if(resMap.containsKey(contextKey)){
                AlgLoopModel target = resMap.get(contextKey);
                loopModel.getModels().forEach(item->{
                    String actionKey = contextKey + "_" + item.getResourceId().trim();
                    target.getModels().removeIf(ps->ps.getName().equals(actionKey));
                    target.getModels().add(item);
                });
            }else{
                this.resources.add(loopModel);
            }
        }
        //更新完毕
        this.isUpdating=false;
    }

    public void print(){}

    private Boolean isCanPoll(){
        return this.currentLoop.size()==0 && this.resources.size()>0 &&!this.isUpdating;
    }

    private void sorted(){
        this.resources = this.resources.stream().sorted(Comparator.comparing(AlgLoopModel::getOrder))
                .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
        AlgLoopModel currentLoopModel = this.currentLoop.peekFirst();
        if(currentLoopModel!=null){
            this.resources.removeIf(it->it.getContext().equals(currentLoopModel.getContext()));
            this.resources.add(currentLoopModel);
        }
    }

    public void start(){
        this.currentLoop = new ConcurrentLinkedDeque<>();
        this.actions = new ConcurrentHashMap<>();
        this.isUpdating=false;
        ExecutorService service =  Executors.newSingleThreadExecutor();
        Thread pollThread = new Thread(()->{
            while (true){
                try {
                    Thread.sleep(1*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isCanPoll()){
                    this.poll();
                }
            }
        });
        service.submit(pollThread);
        this.pollService = service;
    }

    private void poll(){
        //轮询之前先前置排序
        this.sorted();
        AlgLoopModel poll = this.resources.poll();
        if(poll!=null){
            this.resources.add(poll);
        }
        if(poll!=null && poll.isEnable()){
            this.currentLoop.poll();
            this.currentLoop.push(poll);
            log.debug("AlgBucket"+":"+this.bucketName+":"+poll.getContext()+":"+"poll."+" 轮询周期duration:"+poll.getDuration()+"秒  轮询位置 order:"+poll.getOrder());
            Task actionTask =new Task();
            actionTask.init(poll,po->{
                po.getModels().forEach(it -> {
                    AlgAction action = new AlgAction(it, httpManager);
                    String actionKey = action.getName();
                    this.actions.put(actionKey, action);
                    action.start();
                });
                return true;
            },po -> {
                //轮询时间到了，根据isUpdating判断是否暂停
                pauseTask();
                String context = po.getContext();
                this.actionTaskService.stop(context);
                //将上一批全部清理掉
                this.actions.values().forEach(AlgAction::stop);
                this.actions.clear();
                this.currentLoop.clear();
                return true;
            });
            //开始任务
            this.actionTaskService.add(actionTask);
        }
    }

    private void pauseTask() {
        while (this.isUpdating){
            try {
                Thread.sleep(1*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        log.debug("AlgBucket "+":"+this.bucketName+":"+" poll.");
        this.resources.forEach(it->{
            this.actionTaskService.stop(it.getContext());
        });
        this.pollService.shutdown();
        this.pollService=null;
        this.actions.forEach((k,val)->{
            val.stop();
        });
        this.actions.clear();
        this.resources.clear();
    }
}