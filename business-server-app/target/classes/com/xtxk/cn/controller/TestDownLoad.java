package com.xtxk.cn.controller;


import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xtxk.cn.dto.hik.*;
import com.xtxk.cn.dto.build.BuildingDevicePO;
import com.xtxk.cn.entity.RecordParam;
import com.xtxk.cn.mapper.BuildingInfoMapper;
import com.xtxk.cn.service.hk.HKService;
import com.xtxk.cn.service.impl.alarm.VideoManager;
import com.xtxk.cn.utils.http.HutoolHttpClient;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TestDownLoad {

    @Autowired
    private VideoManager videoManager;

    @Value("${record.downloadUrl}")
    private String downloadUrl;

    @Autowired
    private HKService hkService;

    @Autowired
    private BuildingInfoMapper buildingInfoMapper;


    @Test
    public void TestDownLoadVideo() throws Exception {
        RecordParam param = new RecordParam();
        param.setRoom("10001084790");
        param.setFromtime("2023-12-18 13:21:41");
        param.setTotime("2023-12-18 13:22:01");
        JSONObject object = JSONUtil.parseObj(param);

        HutoolHttpClient client = new HutoolHttpClient();
        HttpResponse response = client.httpGet(downloadUrl, object);
        // 获取输入流对象
        InputStream inputStream = response.bodyStream();
        String filePath = videoManager.writeRecordToMinio(inputStream);
        System.out.println(filePath);


//        String filePath = "record.mp4"; // 要保存的文件路径及名称
//        FileOutputStream outputStream = null;
//
//        try {
//            outputStream = new FileOutputStream(filePath); // 创建输出流对象
//            byte[] buffer = new byte[1024]; // 定义字节数组作为缓冲区
//            int length;
//            while ((length = inputStream.read(buffer)) != -1) { // 从输入流读取内容并写入输出流
//                outputStream.write(buffer, 0, length);
//            }
//
//            System.out.println("成功保存文件！");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (inputStream != null) {
//                inputStream.close(); // 关闭输入流
//            }
//            if (outputStream != null) {
//                outputStream.close(); // 关闭输出流
//            }
//        }

//
//        File file = new File(filePath);
//        try {
//            // 调用HuTool工具类的writeFromStream()方法进行保存操作
//            File file1 = FileUtil.writeFromStream(inputStream, file, true);
//        } finally {
//            // 关闭输入流
//            inputStream.close();
//        }
    }

    @Test
    public void TestTime() throws ParseException {
        Date date = DateUtils.parseDate("2023-12-18 13:21:41", "yyyy-MM-dd HH:mm:ss");
        // 创建SimpleDateFormat对象并指定格式化模式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long curTime = date.getTime();
        Date fromTime = new Date(curTime - 10 * 1000);
        Date toTime = new Date(curTime + 10 * 1000);

        System.out.println(sdf.format(fromTime));
        System.out.println(sdf.format(toTime));


    }

    @Test
    public void TestGetHKResources() throws Exception {
        HKResourcesReq request = new HKResourcesReq();
        HKCameraResourceResp result = hkService.getHKResources(request);

        System.out.println(result);
    }

    @Test
    public void TestGetHKCameraPreviewUrl() throws Exception {
        HKCameraPreviewUrlReq req = new HKCameraPreviewUrlReq();
        req.setDeviceId("fc6e40aba100408e88026ff1b072c876");
        req.setProtocol("rtsp");
        HKCameraResp hkCameraResp = hkService.getHKCameraPreviewUrl(req);

        System.out.println(hkCameraResp);
    }

    @Test
    public void TestGetHKCameraPlaybackUrl() throws Exception {
        HKCameraPlaybackUrlReq req = new HKCameraPlaybackUrlReq();
        req.setDeviceId("fc6e40aba100408e88026ff1b072c876");
        req.setProtocol("rtsp");
        req.setBeginDate("2024-01-11");
        req.setBeginTime("08:00:00");
        req.setEndDate("2024-01-11");
        req.setEndTime("09:00:00");

        HKCameraResp hkCameraResp = hkService.getHKCameraPlaybackUrl(req);

        System.out.println(hkCameraResp);
    }

    @Test
    public void TestGetBuildingDeviceListByIdentity(){
        List<BuildingDevicePO> buildingDeviceListByIdentity = buildingInfoMapper.getBuildingDeviceListByIdentityId("344a2cac-4360-4dbb-b0ab-bc3134ed601e");
        System.out.println(buildingDeviceListByIdentity);
    }

}

