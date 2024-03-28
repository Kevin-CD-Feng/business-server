package com.xtxk.recognition.prepare.service.svc.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xtxk.recognition.prepare.service.algAction.AlgAction;
import com.xtxk.recognition.prepare.service.algAction.AlgBucket;
import com.xtxk.recognition.prepare.service.algAction.AlgDispatcher;
import com.xtxk.recognition.prepare.service.component.ICanPushAlarm;
import com.xtxk.recognition.prepare.service.dto.alarmDefine.*;
import com.xtxk.recognition.prepare.service.entity.AlarmDefine;
import com.xtxk.recognition.prepare.service.entity.DicItem;
import com.xtxk.recognition.prepare.service.enums.ErrorCode;
import com.xtxk.recognition.prepare.service.exception.ServiceException;
import com.xtxk.recognition.prepare.service.manager.ImageManager;
import com.xtxk.recognition.prepare.service.mapper.AlarmDefineMapper;
import com.xtxk.recognition.prepare.service.mapper.DicItemMapper;
import com.xtxk.recognition.prepare.service.parser.AlgormParser;
import com.xtxk.recognition.prepare.service.parser.model.XCLRXRJsonEntity;
import com.xtxk.recognition.prepare.service.svc.AlarmDefineService;
import com.xtxk.recognition.prepare.service.utils.ImageUtils;
import com.xtxk.recognition.prepare.service.utils.ObjectCovertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;


@Service
@Slf4j
public class AlarmDefineServiceImpl implements AlarmDefineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmDefineServiceImpl.class);

    @Autowired
    private AlarmDefineMapper alarmDefineMapper;

    @Autowired
    private DicItemMapper dicItemMapper;

    @Autowired
    private ImageManager imageManager;

    @Value("${scheduling.keys}")
    private List<String> keys;

    @Value("${scheduling.personConf}")
    private String personConf;

    @Value("${scheduling.carConf}")
    private String carConf;

    @Autowired
    private List<ICanPushAlarm> alarmsProviders;

    @Autowired
    private AlgDispatcher algDispatcher;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(AddAlarmDefineReqDto addAlarmDefineReqDto) {
        /** 校验告警事件是否存在 */
        checkEvent(addAlarmDefineReqDto.getEventCode());

        /** 校验告警事件对应的事件类型是否正确 */
        checkRelation(addAlarmDefineReqDto.getEventCode(), addAlarmDefineReqDto.getTypeCode());

        AlarmDefine alarmDefine = ObjectCovertUtils.dtoToDo(addAlarmDefineReqDto, AlarmDefine.class);
        if (ObjectUtil.isEmpty(alarmDefine)) {
            throw new ServiceException(ErrorCode.ALARM_DATA_COVERT_ERROR);
        }
        /** 根据事件编码查询事件名称 */
        String eventName = dicItemMapper.queryItemNameByItemCode(alarmDefine.getEventCode());
        if (ObjectUtil.isEmpty(eventName)) {
            throw new ServiceException(ErrorCode.ALARM_EVENT_NAME_NOT_EXIST);
        }
        alarmDefine.setEventName(eventName);
        alarmDefine.setCreateTime(new Date());
        alarmDefine.setCreateUser("admin");
        alarmDefineMapper.insert(alarmDefine);
    }

    @Override
    public DetailAlarmDefineRespDto detail(Integer id) {
        AlarmDefine detail = alarmDefineMapper.queryById(id);
        if (ObjectUtil.isEmpty(detail)) {
            throw new ServiceException(ErrorCode.ALARM_EVENT_NOT_ESIXT);
        }
        return ObjectCovertUtils.doToDto(detail, DetailAlarmDefineRespDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateAlarmDefineReqDto updateAlarmDefineReqDto) {
        /** 校验告警事件是否存在 */
        checkAlarmInfo(updateAlarmDefineReqDto.getId());

        checkRelation(updateAlarmDefineReqDto.getEventCode(), updateAlarmDefineReqDto.getTypeCode());

        AlarmDefine define = ObjectCovertUtils.dtoToDo(updateAlarmDefineReqDto, AlarmDefine.class);
        define.setUpdateTime(new Date());
        define.setUpdateUser("admin");
        /** 根据事件编码查询事件名称 */
        String eventName = dicItemMapper.queryItemNameByItemCode(define.getEventCode());
        if (ObjectUtil.isEmpty(eventName)) {
            throw new ServiceException(ErrorCode.ALARM_EVENT_NAME_NOT_EXIST);
        }
        define.setEventName(eventName);
        Integer update = alarmDefineMapper.update(define);
        if (update == 0) {
            throw new ServiceException(ErrorCode.UPDATE_ALARM_EVENT_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer[] ids) {

        for (Integer id : ids) {
            /** 校验告警事件是否存在 */
            checkAlarmInfo(id);

            /** 是否绑定设备 */
            checkCorrelationDevice(id);

            /** 是否绑定算法 */
            checkCorrelationAlgorithmic(id);
        }
        Integer delete = alarmDefineMapper.delete(ids, new Date(), "admin");
        if (delete == 0) {
            throw new ServiceException(ErrorCode.DELETE_EVENT_ERROR);
        }
    }

    @Override
    public PageAlarmDefineRespDto pageList(PageAlarmDefineReqDto pageAlarmDefineReqDto) {
        PageHelper.startPage(pageAlarmDefineReqDto.getPageNo(), pageAlarmDefineReqDto.getPageSize());
        List<AlarmDefine> alarmDefines = alarmDefineMapper.queryPageList(pageAlarmDefineReqDto);
        List<AlarmDefineListItemDto> alarmDefineItemDtos = new ArrayList<>();
        if (alarmDefines != null && alarmDefines.size() > 0) {
            alarmDefines.forEach(item -> {
                AlarmDefineListItemDto alarmDefineListItemDto = ObjectCovertUtils.doToDto(item, AlarmDefineListItemDto.class);
                String typeName = dicItemMapper.queryItemNameByItemCode(item.getTypeCode());
                alarmDefineListItemDto.setTypeName(typeName);
                alarmDefineItemDtos.add(alarmDefineListItemDto);
            });
        }

        PageAlarmDefineRespDto pageAlarmDefineRespDto = new PageAlarmDefineRespDto();
        PageInfo<AlarmDefineListItemDto> pageInfo = new PageInfo<>(alarmDefineItemDtos);
        pageAlarmDefineRespDto.setCount(pageInfo.getTotal());
        pageAlarmDefineRespDto.setPageCount(pageInfo.getPages());
        pageAlarmDefineRespDto.setList(alarmDefineItemDtos);
        return pageAlarmDefineRespDto;
    }

    /**
     * 是否绑定算法
     *
     * @param id
     */
    private void checkCorrelationAlgorithmic(Integer id) {
        AlarmDefine define = alarmDefineMapper.checkCorrelationAlgorithmic(id);
        if (ObjectUtil.isNotEmpty(define)) {
            throw new ServiceException(ErrorCode.EVENT_BINDING_ALGORITHMIC_ERROR);
        }
    }

    /**
     * 是否绑定设备
     *
     * @param id
     */
    private void checkCorrelationDevice(Integer id) {
        AlarmDefine define = alarmDefineMapper.checkCorrelationDevice(id);
        if (ObjectUtil.isNotEmpty(define)) {
            throw new ServiceException(ErrorCode.EVENT_BINDING_DEVICE_ERROR);
        }
    }

    /**
     * 校验告警事件是否存在
     *
     * @param eventCode
     * @return
     */
    public void checkEvent(String eventCode) {
        AlarmDefine alarmDefine = alarmDefineMapper.checkEventCode(eventCode);
        if (ObjectUtil.isNotEmpty(alarmDefine)) {
            throw new ServiceException(ErrorCode.ALARM_EVENT_ESIXT);
        }
    }

    @Override
    public List<Map<String, Object>> queryEventTypeList() {

        /** 根据事件类型编码查询事件类型列表 */
        List<DicItem> dicItems = dicItemMapper.queryDicItemList("alarm_type");
        if (dicItems != null && dicItems.size() > 0) {
            List<Map<String, Object>> eventTypes = new ArrayList<>();
            dicItems.forEach(item -> {
                Map<String, Object> map = new HashMap<>();
                map.put("desc", item.getItemName());
                map.put("code", item.getItemCode());
                eventTypes.add(map);
            });
            return eventTypes;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Map<String, Object>> type2Event(String eventType) {
        List<DicItem> dicItems = dicItemMapper.queryByItemParentCode(eventType);
        if (dicItems != null && dicItems.size() > 0) {
            List<Map<String, Object>> eventTypes = new ArrayList<>();
            dicItems.forEach(item -> {
                Map<String, Object> map = new HashMap<>();
                map.put("desc", item.getItemName());
                map.put("code", item.getItemCode());
                eventTypes.add(map);
            });
            return eventTypes;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Boolean canPushAlarm(CanPushAlarmReqDto canPushAlarmReqDto) {
        String[] splits = {canPushAlarmReqDto.getAlgCode(), canPushAlarmReqDto.getEventCode()};
        String bucketName = String.join("_", splits);
        String[] actions = {canPushAlarmReqDto.getAlgCode(), canPushAlarmReqDto.getEventCode(), canPushAlarmReqDto.getResourceId()};
        String actionKey = String.join("_", actions);
        Boolean flag = false;
        for (ICanPushAlarm alarm : alarmsProviders) {
            if (alarm.isSupport(canPushAlarmReqDto.getEventCode())) {
                flag = alarm.canPushAlarm(canPushAlarmReqDto.getData(), bucketName, canPushAlarmReqDto.getResourceId());
                break;//跳出去
            }
        }
        return flag;
    }

    @Override
    public HashMap<String, Integer> getVioResource(CanPushAlarmReqDto canPushAlarmReqDto) {
        HashMap<String, Integer> flag = new HashMap<>();
        for (ICanPushAlarm alarm : alarmsProviders) {
            if (alarm.isSupport(canPushAlarmReqDto.getEventCode())) {
                flag = alarm.getVioResource(canPushAlarmReqDto.getData(), canPushAlarmReqDto.getResourceId(),
                        canPushAlarmReqDto.getFeatures());
                break;//跳出去
            }
        }
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dealAlarmInfo(PushAlarmInfoReqDto dealAlarmInfoReqDto) throws IOException {
        //这个方法中主要工作如下:
        //第一步：画框
        //第二步：返回图片

        String data = dealAlarmInfoReqDto.getData();
        String eventCode = dealAlarmInfoReqDto.getEventCode();
        List<Integer> points = new ArrayList<>();
        HashMap<String, List<List<Integer>>> coordinates = new HashMap<>();
        for (ICanPushAlarm alarm : alarmsProviders) {
            if (alarm.isSupport(eventCode)) {
                points = alarm.getPoints(data);
                coordinates = alarm.getCoordinate(data);
                break;//跳出去
            }
        }
        if (points.size() > 0) {
            log.info("DrawRIOTOImage start.");
            DrawRIOTOImage(dealAlarmInfoReqDto, points, coordinates);
            log.info("DrawRIOTOImage end.");
        }
    }

    @Async("taskExecutor")
    public void DrawRIOTOImage(PushAlarmInfoReqDto dealAlarmInfoReqDto, List<Integer> points, HashMap<String, List<List<Integer>>> coordinates) throws IOException {
        //将其从minio中拿出来
        String oriImage = imageManager.readFromMinio(dealAlarmInfoReqDto.getPath());
        //画图 Draw
        String image = dealImage(oriImage, dealAlarmInfoReqDto.getRoi(), points, coordinates, keys);
        //将其放回去
        imageManager.writeToMinio(dealAlarmInfoReqDto.getPath(), image);
    }

    /**
     * 处理原始图片
     *
     * @param oriImage
     * @return
     */

    public String dealImage(String oriImage, Integer[][] roi, List<Integer> json, HashMap<String, List<List<Integer>>> coordinates, List<String> keys) throws IOException {
        /** 绘制区域范围 */
        if (roi == null) { //防止初始化
            roi = new Integer[][]{};
        }
        String roiImageStr = drawRoi(roi, oriImage);
        List<Integer> data = json;// parseJson.parseJson(json, keys);
        String[] base = roiImageStr.split(",");
        byte[] decode = Base64.decode(base[base.length - 1]);
        byte[] image = draw(decode, data, coordinates, "jpeg");
        return Base64.encode(image);
    }

    /***
     * 绘制图片区域
     * @param imageByte
     * @param points
     * @param imageFormatter
     * @return
     * @throws IOException
     */
    public byte[] draw(byte[] imageByte, List<Integer> points, HashMap<String, List<List<Integer>>> coordinates, String imageFormatter) throws IOException {
        if (points == null || points.size() == 0) {
            return imageByte;
        }
        // TODO 存在多个识别区域 【position [935,298,155,260]】 每4个点为一组
        List<List<Integer>> morePoints = Lists.partition(points, 4);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageByte));
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        //设置线条宽度
        graphics.setStroke(new BasicStroke(8));
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("微软雅黑", Font.ITALIC, 20));
        morePoints.forEach(f -> {
            //格式点坐标x轴  y轴 宽度 高度
            graphics.drawRect(f.get(0), f.get(1), f.get(2), f.get(3));
        });

        //人车移动轨迹画虚线
        ArrayList<Integer> carXList = new ArrayList<>();    // 车辆X坐标
        ArrayList<Integer> carYList = new ArrayList<>();    //车辆Y坐标
        ArrayList<Integer> personXList = new ArrayList<>(); //人员X坐标
        ArrayList<Integer> personYList = new ArrayList<>(); //人员Y坐标

        coordinates.forEach((name, coordinate) -> {
            if (name.equals("car")) { //车辆移动轨迹坐标
                coordinate.forEach(i -> {
                    carXList.add(i.get(0));
                    carYList.add(i.get(1));
                });

            }

            if (name.equals("person")) {
                coordinate.forEach(i -> { //人员移动轨迹坐标
                    personXList.add(i.get(0));
                    personYList.add(i.get(1));
                });
            }
        });

        int[] carXPoints = carXList.stream().mapToInt(i -> i).toArray();
        int[] carYPoints = carYList.stream().mapToInt(i -> i).toArray();
        int[] personXPoints = personXList.stream().mapToInt(i -> i).toArray();
        int[] personYPoints = personYList.stream().mapToInt(i -> i).toArray();

        // 设置线条颜色、宽度等属性
        graphics.setColor(Color.BLUE);
        // 设置虚线样式
        BasicStroke dashedStroke = new BasicStroke(6f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0);
        graphics.setStroke(dashedStroke);

        // 调用drawPolyline()方法进行绘制
        graphics.drawPolyline(carXPoints, carYPoints, carXPoints.length);//车辆移动轨迹
        graphics.drawPolyline(personXPoints, personYPoints, personXPoints.length);//人员移动轨迹

        //关闭绘制
        graphics.dispose();
        //将绘制后的图片获取字节
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, imageFormatter, out);
        byte[] bytes = out.toByteArray();
        IOUtils.closeQuietly(out);
        return bytes;
    }

    /**
     * 绘制范围区域
     *
     * @param roi
     * @param image
     * @return
     */
    private String drawRoi(Integer[][] roi, String image) {
        byte[] decode = Base64.decode(image);
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(decode));
            Graphics graphics = bufferedImage.getGraphics();

            graphics.setFont(new Font("微软雅黑", Font.ITALIC, 20));
            graphics.setColor(Color.YELLOW);

            for (Integer[] integers : roi) {
                List<Integer> xPoints = new ArrayList<>();
                List<Integer> yPoints = new ArrayList<>();
                for (int j = 0; j < integers.length; j++) {
                    if (j % 2 == 0) {
                        xPoints.add(integers[j]);
                    } else {
                        yPoints.add(integers[j]);
                    }
                }
                graphics.drawPolygon(xPoints.stream().mapToInt(Integer::valueOf).toArray(), yPoints.stream().mapToInt(Integer::valueOf).toArray(), xPoints.size());
                graphics.drawString("roi", xPoints.get(0), yPoints.get(0));
            }
            return getFinalImg(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将bufferedImage转化为base64字符串
     *
     * @param bufferedImage
     * @return
     */
    private String getFinalImg(BufferedImage bufferedImage) {
        return ImgUtil.toBase64(bufferedImage, "jpg");
    }


    /**
     * 校验告警事件对应的事件类型是否正确
     *
     * @param eventCode
     * @param typeCode
     */
    private void checkRelation(String eventCode, String typeCode) {
        String typeByCode = dicItemMapper.queryItemParentCodeByItemCode(eventCode);
        if (!typeByCode.equals(typeCode)) {
            throw new ServiceException(ErrorCode.ALARM_EVENT_TYPE_NOT_MATCH);
        }
    }

    /**
     * 根据id校验告警事件
     *
     * @param id
     */
    private void checkAlarmInfo(Integer id) {
        AlarmDefine detail = alarmDefineMapper.queryById(id);
        if (ObjectUtil.isEmpty(detail)) {
            throw new ServiceException(ErrorCode.ALARM_EVENT_NOT_ESIXT);
        }
    }
}