package com.xtxk.recognition.prepare.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Joiner;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xtxk.recognition.prepare.service.algAction.AlgBucket;
import com.xtxk.recognition.prepare.service.algAction.AlgLoopModel;
import com.xtxk.recognition.prepare.service.algAction.AlgModel;
import com.xtxk.recognition.prepare.service.component.ICanPushAlarm;
import com.xtxk.recognition.prepare.service.manager.ImageManager;
import com.xtxk.recognition.prepare.service.mock.AlarmFileReader;
import com.xtxk.recognition.prepare.service.svc.impl.AlarmDefineServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TestDemo {

    @Autowired
    private List<ICanPushAlarm> provider;

    @Autowired
    private ImageManager imageManager;

    @Autowired
    private AlarmDefineServiceImpl alarmDefineService;


    @Test
    public void TestProvider(){
        Assert.assertNotNull(this.provider);
    }

    @Test
    public void TestCanPushAlarmWithCrawl() throws JsonProcessingException {
        String cur = System.getProperty("user.dir");
        String fileName = cur+"/"+"json/zgdlgsj_.json";
        String crawJson = FileUtil.readString(fileName, Charset.defaultCharset());
        for(ICanPushAlarm alarm:provider){
        }
    }

    private List<AlgLoopModel> getLoopModels(String prefix){
        List<AlgLoopModel> list = new ArrayList<>();
        AlgLoopModel algLoopModel = new AlgLoopModel();
        algLoopModel.setModels(new ConcurrentLinkedQueue<>(new ArrayList<AlgModel>()));
        algLoopModel.setOrder(1);
        algLoopModel.setContext("loop+"+prefix);
        AlgModel algModel = AlgModel.builder().build();
        algModel.setAlgCode("it.getAlgCode()"+prefix);
        algModel.setEventCode("it.getAlgCode()"+prefix);
        algModel.setResourceId("it.getAlgCode()"+prefix);
        algModel.setResourceName("it.getAlgCode()"+prefix);
        algModel.setWebCoordinate("it.getAlgCode()"+prefix);
        algModel.setFrameInterval(0F);
        algLoopModel.setContext("it.getAlgCode()"+prefix);
        //数据库中是分钟为单位，传值修改为秒
        algModel.setDuration(60);
        algModel.setInterval(60);
        algLoopModel.getModels().add(algModel);
        list.add(algLoopModel);

        algLoopModel = new AlgLoopModel();
        algLoopModel.setContext("loop2+"+prefix);
        algLoopModel.setModels(new ConcurrentLinkedQueue<>(new ArrayList<AlgModel>()));
        algLoopModel.setOrder(1);
        algModel = AlgModel.builder().build();
        algModel.setAlgCode("it.getAlgCode2()"+prefix);
        algModel.setEventCode("it.getAlgCod2e()"+prefix);
        algModel.setResourceId("it.getAlgCode2()"+prefix);
        algModel.setResourceName("it.getAlgCode2()"+prefix);
        algModel.setWebCoordinate("it.getAlgCod2e()"+prefix);
        algModel.setFrameInterval(0F);
        algLoopModel.setContext("it.getAlgCode2()"+prefix);
        //数据库中是分钟为单位，传值修改为秒
        algModel.setDuration(60);
        algModel.setInterval(60);
        algLoopModel.getModels().add(algModel);
        list.add(algLoopModel);


        return list;
    }

    @Test
    public void TestAlgBucket() throws InterruptedException {
        AlgBucket bucket = new AlgBucket();
        List<AlgLoopModel> loopModels = getLoopModels(" main");
        //bucket.reloadResources(loopModels);
        ExecutorService service =  Executors.newSingleThreadExecutor();
        Thread pollThread = new Thread(()->{
            while (true){
                try {
                    Thread.sleep(2*1000);
                    List<AlgLoopModel> loopModels1 = getLoopModels("thread1");
                    //bucket.reloadResources(loopModels1);
                } catch (InterruptedException e) {

                }
            }
        });
        service.submit(pollThread);

        ExecutorService service1 =  Executors.newSingleThreadExecutor();
        Thread pollThread1 = new Thread(()->{
            while (true){
                try {
                    Thread.sleep(5*1000);
                    List<AlgLoopModel> loopModels1 = getLoopModels("thead2");
                    //bucket.reloadResources(loopModels1);
                } catch (InterruptedException e) {

                }
            }
        });
        service1.submit(pollThread1);

        ExecutorService service2 =  Executors.newSingleThreadExecutor();
        Thread pollThread2 = new Thread(()->{
            while (true){
//                ConcurrentLinkedQueue<AlgLoopModel> resources = bucket.getResources();
//                resources.forEach(it->{
//                    System.out.println(it.getContext());
//                });
            }
        });
        service2.submit(pollThread2);

        Thread.sleep(60*10*1000);
    }


    @Test
    public void TestDeque(){
        ConcurrentLinkedDeque<String> strings = new ConcurrentLinkedDeque<>();
        strings.poll();
        strings.add("1");
        strings.add("2");
        strings.forEach(System.out::println);
        System.out.println("-------------");
        strings.push("3");
        strings.forEach(System.out::println);
        System.out.println("-------------");
        String poll = strings.poll();
        strings.forEach(System.out::println);
        System.out.println("-------------");
        strings.add(poll);
        strings.forEach(System.out::println);
        System.out.println("-------------");
    }

    @Test
    public void TestStringSubBefore(){
        String[] splits= {"algCode","eventCode","algOrder","resourceId"};
        String join = String.join("_", splits);
        System.out.println(join);

        System.out.println(StrUtil.subBefore(join,"_",true));
    }

    static class Pos{
        int val;
        Pos next;

        Pos(int val,Pos next){
            this.val =val;
            this.next = next;
        }
    }

    static boolean isSafe(Pos ps,int y,int gap){
        return ps==null||ps.val!=y && ps.val+gap !=y &&ps.val-gap!=y && isSafe(ps.next,y,gap+1);
    }

    @Test
    public void TestSetDiff(){
//        List<String> A = Stream.of("A","C","D").collect(Collectors.toList());
//        List<String> B = Stream.of("H").collect(Collectors.toList());
//        Set<String> AsET = new HashSet<>(A);
//        Set<String> BsET= new HashSet<>(B);

        long count = Stream.iterate(Stream.of((Pos) null),
                r -> r.flatMap(ps -> IntStream.rangeClosed(1, 8)
                                    .filter(y -> isSafe(ps, y, 1))
                                    .boxed()
                                    .map(y -> new Pos(y, ps))
                ))
                .skip(8)
                .findFirst().orElse(null)
                .count();
        System.out.println(count);
    }

    @Test
    public void TestMinio() throws IOException {
        String cur = System.getProperty("user.dir");
        String outputfile = cur+"/"+"json/mex.json";
        String oriImage = imageManager.readFromMinio("/2023-09-04 14:06:35/41830b9a-ae49-40b6-9fce-3a8ec3b14a0b.jpeg");
        Integer[][] roi = {{50,50,50,50}};
        List<Integer> collect = Stream.of(20, 200, 300, 20).collect(Collectors.toList());
        String s = alarmDefineService.dealImage(oriImage, roi, collect,null, null);
    }


    @Autowired
    private AlarmFileReader alarmFileReader;

    @Test
    public void TestPulseSendMessage(){
        alarmFileReader.sendByDuration();
    }

    @Test
    public void TestDateTick(){
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//        String format = sf.format(new Date(1694170514));
//        System.out.println(format);
    }

    @Test
    public void TestGuava() throws InterruptedException {
        //String context = Joiner.on(".").join(Stream.of("", "").toArray());

        Cache<String, Object> build = CacheBuilder.newBuilder().initialCapacity(1000)
                .removalListener(target -> {
                    System.out.println("time expires:"+JSONUtil.toJsonStr(target));
                })
                .expireAfterAccess(4,TimeUnit.SECONDS)
                .build();

        build.put("me","derek");
        //build.invalidate("me");

        Thread.sleep(11*1000);

        build.getIfPresent("me");
    }
}