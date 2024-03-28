package com.xtxk.recognition.prepare.service.algAction;

import cn.hutool.core.convert.ConverterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;

@Component
@Slf4j
public class ActionTaskService {
    public Map<String, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<>();
    public List<String> taskList = new CopyOnWriteArrayList<String>();
    private final ThreadPoolTaskScheduler syncScheduler;

    public ActionTaskService(ThreadPoolTaskScheduler syncScheduler) {
        this.syncScheduler = syncScheduler;
    }

    public List<String> getTaskList() {
        return taskList;
    }

    public boolean add(Task task) {
        // 此处的逻辑是 ，如果当前已经有这个名字的任务存在，先删除之前的，再添加现在的。（即重复就覆盖）
        if (null != taskMap.get(task.getName())) {
            stop(task.getName());
        }

        // hutool 工具包下的一个转换类型工具类 好用的很
        ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
        Date startTime = converterRegistry.convert(Date.class, task.getStart());

        // schedule :调度给定的Runnable ，在指定的执行时间调用它。
        //一旦调度程序关闭或返回的ScheduledFuture被取消，执行将结束。
        //参数：
        //任务 – 触发器触发时执行的 Runnable
        //startTime – 任务所需的执行时间（如果这是过去，则任务将立即执行，即尽快执行）
        ScheduledFuture<?> schedule = syncScheduler.schedule(getRunnable(task), startTime);
        taskMap.put(task.getName(), schedule);
        taskList.add(task.getName());
        return true;
    }

    public Runnable getRunnable(Task task) {
        return () -> {
            try {
                //单位为秒
                if(task.getAction()!=null){
                    task.getAction().test(task.getModel());
                }
                Integer interval = task.getInterval();
                Thread.sleep(interval*1000);
            } catch (InterruptedException e) {
                return;
            }
            if(task.getNotify()!=null){
                task.getNotify().test(task.getModel());
            }
        };
    }

    public boolean stop(String name) {
        if (null == taskMap.get(name)) {
            return false;
        }
        ScheduledFuture<?> scheduledFuture = taskMap.get(name);
        scheduledFuture.cancel(true);
        taskMap.remove(name);
        taskList.remove(name);
        return true;
    }
}