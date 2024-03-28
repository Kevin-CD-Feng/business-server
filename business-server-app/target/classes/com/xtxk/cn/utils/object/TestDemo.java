package com.xtxk.cn.utils.object;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestDemo {


    @Value("${scheduling.personConf}")
    private Float personConf;

    @Value("${scheduling.carConf}")
    private Float carConf;

    @Value("${scheduling.keys}")
    private List<String> keys;

    @Test
    public void TestXCLRXR() throws JsonProcessingException {
        Float a = Float.parseFloat("0.6");
        System.out.println(a);
        String json="{\n" +
                "  \"code\": 200,\n" +
                "  \"data\": {\n" +
                "    \"event_result\": [{\n" +
                "      \"car_bbox\": [559,284,121,133],\n" +
                "      \"car_conf\": 0.57,\n" +
                "      \"person_bbox\": [759,294,33,118],\n" +
                "      \"person_conf\": 0.8,\n" +
                "      \"violation\": \"yes\"}],\n" +
                "    \"model_result\": []\n" +
                "  }\n" +
                "}";

        //XCLRXRJsonEntity parse = xclrxrJsonParser.parse(json, XCLRXRJsonEntity.class);

        List<Integer> points = new ArrayList<>();
//        XCLRXRJsonEntity jsonEntity = xclrxrJsonParser.parse(json, XCLRXRJsonEntity.class);
//        if(jsonEntity.getData().getEvent_result()!=null||jsonEntity.getData().getEvent_result().size()>0){
//            jsonEntity.getData().getEvent_result().forEach(it->{
//                if(it.getViolation().equals("yes") && it.getPerson_conf()>=Float.parseFloat("0.5") && it.getCar_conf()>=0.5){
//                    points.addAll(it.getCar_bbox());
//                    points.addAll(it.getPerson_bbox());
//                }
//            });
//        }

        System.out.println(points);

    }
}