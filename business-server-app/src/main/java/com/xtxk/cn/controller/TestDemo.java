package com.xtxk.cn.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.configurer.websocket.WebSocketServer;
import com.xtxk.cn.dto.SendToUser;
import com.xtxk.cn.dto.accessRecord.AccessPersonRecordReqDto;
import com.xtxk.cn.dto.accessRecord.AccessPersonRecordRespDto;
import com.xtxk.cn.dto.accessRecord.AccessPersonRecordResult;
import com.xtxk.cn.dto.alarmDefine.PageAlarmDefineReqDto;
import com.xtxk.cn.dto.alarmInfo.PushDeviceOnlineVo;
import com.xtxk.cn.dto.dic.DicSuperiorListRespDto;
import com.xtxk.cn.dto.freemaker.ContentEntity;
import com.xtxk.cn.dto.freemaker.HeaderEntity;
import com.xtxk.cn.dto.freemaker.ImageEntity;
import com.xtxk.cn.entity.AlarmInfo;
import com.xtxk.cn.mapper.MonitorDeviceMapper;
import com.xtxk.cn.service.impl.alarm.ImageManager;
import com.xtxk.cn.utils.http.HttpResponse;
import com.xtxk.cn.utils.http.HutoolHttpClient;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.misc.BASE64Encoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import freemarker.template.Configuration;

import javax.imageio.ImageIO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TestDemo {

    @Autowired
    private ImageManager imageManager;
    @Autowired
    private WebSocketServer socketServer;
    @Autowired
    private MonitorDeviceMapper monitorDeviceMapper;

    @Test
    public void TestImageWriteToMinio() throws IOException {
//        String fileName = "C:\\Program Files\\XT5000A\\PrisonShowImages\\0401-广播系统.jpg";
//        byte [] bytes = FileUtils.readFileToByteArray(new File(fileName));
//        String text = Base64.encodeBase64String(bytes);
//        imageManager.writeToMinio("pc",text);
        //FileUtils.writeStringToFile(new File("G:/Users/a.txt"),text);

        HashMap<String, Integer> map = new HashMap<>();
        map.put("107dc52b0f3647e7aa762808dc3a61e7", 2);
        map.put("Vkkjh", 3);
        String s = JSONUtil.toJsonStr(map);
        System.out.println(s);
    }

    @Test
    public void TestHutoolJsonToStr() throws JsonProcessingException {
        PageAlarmDefineReqDto dto = new PageAlarmDefineReqDto();
        dto.setPageNo(0);
        dto.setPageSize(10);
        JSONObject jsonObject = JSONUtil.parseObj(dto);
        HutoolHttpClient client = new HutoolHttpClient();
        HttpResponse httpResponse = client.postJson("http://localhost:9529/recognization/dic/superiorList", jsonObject);
        System.out.println(httpResponse);
        CommonResponse<DicSuperiorListRespDto> commonResponse =
                JSONUtil.toBean(httpResponse.getJsonResult(), new TypeReference<CommonResponse<DicSuperiorListRespDto>>() {
                }, true);
//        CommonResponse<DicSuperiorListRespDto>.
//        ObjectMapper mapper = new ObjectMapper();
//        CommonResponse commonResponse1 = mapper.readValue(JSONUtil.toJsonStr(commonResponse), CommonResponse.class);
        System.out.println(JSONUtil.toJsonStr(commonResponse));
    }

    @Test
    public void TestAlarmInfo() {
        AlarmInfo info = new AlarmInfo();
        info.setId(12);
        info.setResourceId("resourceId");
        info.setResourceName("resourceName");
        info.setStatus(1);
        info.setAreaName("areaName");
        info.setResourceType("resourceType");
        info.setAreaName("AreaName");
        info.setArea("Area");
        info.setEventName("EventName");
        info.setEvent("Event");
        info.setLatitude(new BigDecimal("23.1"));
        info.setLongitude(new BigDecimal("23.1"));
        info.setCatchImageUrl("http://catchImageUrl");
        info.setCatchTime(new Date());

        SendToUser<AlarmInfo> sendToUser = new SendToUser<AlarmInfo>();
        sendToUser.setFuncName("informAlarmInfo");
        sendToUser.setData(new ArrayList<>());
        sendToUser.getData().add(info);

        //socketServer.sendToAll(JsonUtil.obj2Str(sendToUser));
        System.out.println(JSONUtil.toJsonStr(sendToUser));
    }

    @Test
    public void TestResourceStatus() {
        PushDeviceOnlineVo info = new PushDeviceOnlineVo();
        info.setResId("resId");
        info.setResName("resName");
        info.setResType("resType");
        ;
        info.setResIp("resIp");
        info.setStatus("offline/online");
        info.setDepartId("departId");
        info.setDepartName("departName");
        SendToUser<PushDeviceOnlineVo> sendToUser = new SendToUser<PushDeviceOnlineVo>();
        sendToUser.setFuncName("informAlarmInfo");
        sendToUser.setData(new ArrayList<>());
        sendToUser.getData().add(info);
        System.out.println(JSONUtil.toJsonStr(sendToUser));
    }

    @Test
    public void aa() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time);
        System.out.println(t);
        t = "1694243159953";
        Long tm = Long.parseLong(t);
        Date date = new Date(tm);
        System.out.println(DateUtil.formatDateTime(date));
    }

    @Test
    public void TestDateUtil() throws ParseException {
        Date dateTime = DateUtils.parseDate("2023-09-14 09:44:37", "yyyy-mm-dd HH:mm:ss");
        System.out.println("xxkxkxkxk");
    }


    @Test
    public void TestDate() throws IOException, TemplateException {
        Map<String, Object> tableData = new HashMap<>();
        String current = "E:\\javaworks\\business-server\\docs";
        String img = picture2Base64(current + "/u0.png");
        List<ImageEntity> imageEntities = new ArrayList<>();
        imageEntities.add(new ImageEntity("images1", img));
        tableData.put("imageList", imageEntities);

        List<HeaderEntity> headerEntities = new ArrayList<>();
        headerEntities.add(new HeaderEntity("违规停车"));
        headerEntities.add(new HeaderEntity("翻越围栏"));
        headerEntities.add(new HeaderEntity("不礼让行人"));
        headerEntities.add(new HeaderEntity("不礼让行人11"));
        tableData.put("tableHeader", headerEntities);

        List<ContentEntity> contentEntities = new ArrayList<>();
//        contentEntities.add(new ContentEntity("10"));
//        contentEntities.add(new ContentEntity("12"));
//        contentEntities.add(new ContentEntity("0"));
//        contentEntities.add(new ContentEntity("0"));
        tableData.put("tableContent", contentEntities);

        Configuration configuration = new Configuration();
        File oriFile = new File("E:\\javaworks\\business-server\\docs");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setDirectoryForTemplateLoading(oriFile);

        Template template = configuration.getTemplate("eventReport.ftl", "UTF-8");
        File outFile = new File("E:\\javaworks\\business-server\\docs\\me2.docx");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
        template.process(tableData, out);
    }

    private String picture2Base64(String picturePath) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(picturePath);
            data = new byte[in.available()];
            in.read(data);
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(data);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("");
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    @Test
    public void DrawLineOnImage() throws Exception {
        // 读取原始图片文件
        File input = new File("image/test.jpg");

        BufferedImage image = ImageIO.read(input);

        Graphics2D g2d = (Graphics2D) image.getGraphics();

        // 设置线条颜色、宽度等属性
        g2d.setColor(Color.blue);
//        g2d.setStroke(new BasicStroke(8));

        // 设置虚线样式
        BasicStroke dashedStroke = new BasicStroke(6f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0);
        g2d.setStroke(dashedStroke);

        // 定义起点和终点坐标
//        int x1 = 50;
//        int y1 = 50;
//        int x2 = 200;
//        int y2 = 200;

        // 在图像上画直线
//        g2d.drawLine(x1, y1, x2, y2);

        // 定义要连接的点坐标数组
        int[] xPoints = {0,10, 200, 300, 435};
        int[] yPoints = {0,50, 75, 90, 100};


        // 调用drawPolyline()方法进行绘制
        g2d.drawPolyline(xPoints, yPoints, xPoints.length);


        // 保存修改后的图片到新文件
        File output = new File("image/output.jpg");
        ImageIO.write(image, "jpeg", output);

        System.out.println("已成功将线条添加到图片中并保存为output.jpg！");
    }

    @Test
    public void getCameraCode() throws Exception{
        String cameraCodeByDeviceId = monitorDeviceMapper.getCameraCodeByDeviceId("003d597fda0c40c7990f752597cf83f5");
        System.out.println(cameraCodeByDeviceId);
    }
}



