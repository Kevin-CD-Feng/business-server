package com.xtxk.recognition.prepare.service.algAction;

import com.xtxk.recognition.prepare.service.manager.HttpManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.util.calendar.LocalGregorianCalendar;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDemo {
    @Autowired
    private HttpManager httpManager;

    @Autowired
    private ActionTaskService actionTaskService;

    @Test
    public void TestAlgAction(){
        AlgModel model = AlgModel.builder().build();;
        model.setAlgCode("algCode");
        model.setEventCode("eventCode");
        model.setFrameInterval(10F);
        model.setResourceId("resourceId");
        model.setResourceName("resourceName");
        AlgAction action = new AlgAction(model,httpManager);
        action.start();
        Assert.assertTrue(action.Test("resourceId"));
    }

    @Test
    public void TestAlgActionWithDurationAndIntervals() throws InterruptedException {
        AlgModel model = AlgModel.builder().build();
        model.setAlgCode("algCode");
        model.setEventCode("eventCode");
        model.setFrameInterval(10F);
        model.setResourceId("resourceId");
        model.setResourceName("resourceName");
        model.setDuration(5);
        model.setInterval(0);
        AlgAction action = new AlgAction(model,httpManager);
        action.start();

        while (true){
            boolean resourceId = action.Test("resourceId");
            if(resourceId){
                System.out.println("binggo.");
            }
        }
    }

    private List<AlgModel> genAlgModel(String algCode,String eventCode){
        AlgModel md1 = AlgModel.builder()
                .algCode(algCode)
                .eventCode(eventCode)
                .duration(5)
                .interval(0)
                .frameInterval(5F)
                .resourceId("resourceId1")
                .resourceName("resourceName1")
                .vioTimeDuration("")
                .webCoordinate("")
                .build();
        AlgModel md2 = AlgModel.builder()
                .algCode(algCode)
                .eventCode(eventCode)
                .duration(15)
                .interval(5)
                .frameInterval(5F)
                .resourceId("resourceId2")
                .resourceName("resourceName2")
                .vioTimeDuration("")
                .webCoordinate("")
                .build();

        List<AlgModel> models = new ArrayList<>();
        models.add(md1);
        models.add(md2);
        return models;
    }

    private List<AlgModel> genAlgModel2(String algCode,String eventCode){
        AlgModel md1 = AlgModel.builder()
                .algCode(algCode)
                .eventCode(eventCode)
                .duration(5)
                .interval(0)
                .frameInterval(5F)
                .resourceId("resourceId12")
                .resourceName("resourceName12")
                .vioTimeDuration("")
                .webCoordinate("")
                .build();
        AlgModel md2 = AlgModel.builder()
                .algCode(algCode)
                .eventCode(eventCode)
                .duration(15)
                .interval(5)
                .frameInterval(5F)
                .resourceId("resourceId22")
                .resourceName("resourceName22")
                .vioTimeDuration("")
                .webCoordinate("")
                .build();

        List<AlgModel> models = new ArrayList<>();
        models.add(md1);
        models.add(md2);
        return models;
    }

    @Test
    public void TestAlgBucket(){
        AlgBucket bucket1 = new AlgBucket();
        String algCode ="algCode1";
        String eventCode="eventCode1";
        List<AlgLoopModel> loops = new ArrayList<>();

        AlgLoopModel loop1 =new AlgLoopModel();
        loop1.init(20,algCode+eventCode+"_1",this.genAlgModel(algCode,eventCode));
        loops.add(loop1);

        AlgLoopModel loop2 =new AlgLoopModel();
        loop2.init(10,algCode+eventCode+"_2",this.genAlgModel2(algCode,eventCode));
        loops.add(loop2);

        bucket1.init(this.actionTaskService,this.httpManager,loops);
        bucket1.start();

        while (true){
            bucket1.getActions().values().forEach(it->{
                it.Test("");
            });
        }
    }
}