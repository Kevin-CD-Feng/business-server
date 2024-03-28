package com.xtxk.cn.service.impl.statistic;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.aspose.words.*;
import com.xtxk.cn.dto.AlarmStatusCount;
import com.xtxk.cn.dto.statistic.event.*;
import com.xtxk.cn.entity.DicItem;
import com.xtxk.cn.enums.AlarmEventStateEnum;
import com.xtxk.cn.mapper.AlarmInfoMapper;
import com.xtxk.cn.mapper.DicItemMapper;
import com.xtxk.cn.service.statistic.EventStatisticService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class EventStatisticServiceImpl implements EventStatisticService {

    @Autowired
    private AlarmInfoMapper alarmInfoMapper;
    @Autowired
    private DicItemMapper dicItemMapper;

    static {
        final String licenseKey = "<License><Data><Products><Product>Aspose.Total for Java</Product><Product>Aspose.Words for Java</Product></Products><EditionType>Enterprise</EditionType><SubscriptionExpiry>20991231</SubscriptionExpiry><LicenseExpiry>20991231</LicenseExpiry> <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber></Data><Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature></License>";
        License license = new License();
        try {
            license.setLicense(new ByteArrayInputStream(licenseKey.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /***
     * 事件状态统计
     * @return
     */
    @Override
    public List<AlarmStatusCount> status(EventParam param) {
        ParamDTO paramDTO = convert(param);
        List<AlarmStatusCount> list = alarmInfoMapper.statisticStatus(paramDTO);
        Integer total = list.stream().mapToInt(AlarmStatusCount::getCount).sum();
        for (AlarmStatusCount count : list) {
            count.setStatusStr(AlarmEventStateEnum.queryDescByCode(count.getStatus()));
            count.setPercent(new DecimalFormat("##.##%").format((float) count.getCount() / total));
        }
        return list;
    }

    /***
     * 告警处理统计
     * @param eventParam
     * @return
     */
    @Override
    public List<HandVo> handle(EventParam eventParam) {
        ParamDTO paramDTO = convert(eventParam);
        return alarmInfoMapper.statistician(paramDTO);
    }

    /***
     * 事件类型统计
     * @param eventParam
     * @return
     */
    @Override
    public List<EventTypeHandleVo> type(EventParam eventParam) {
        List<EventTypeHandleVo> result = new ArrayList<>();
        List<DicItem> alarm_event = dicItemMapper.queryDicItemListByDicCode("alarm_event");
        ParamDTO paramDTO = convert(eventParam);
        List<EventHandleDTO> list = alarmInfoMapper.statisticTypeHandle(paramDTO);
        Map<String, EventHandleDTO> handMap = list.stream().collect(Collectors.toMap(p -> p.getEventCode(),
                Function.identity(), (key1, key2) -> key2));
        for (DicItem item : alarm_event) {
            EventTypeHandleVo vo = new EventTypeHandleVo();
            vo.setEventCode(item.getItemCode());
            vo.setEventName(item.getItemName());
            EventHandleDTO handleDTO = handMap.get(item.getItemCode());
            if (ObjectUtil.isNotNull(handleDTO)) {
                vo.setNumber(handleDTO.getNumber());
                vo.setTotal(handleDTO.getTotal());
            } else {
                vo.setNumber(0);
                vo.setTotal(0);
            }
            result.add(vo);
        }
        return result;
    }

    /***
     * 列表数据
     * @param eventParam
     * @return
     */
    @Override
    public List<TableDataVo> table(EventParam eventParam) {
        List<TableDataVo> result = new ArrayList<>();
        ParamDTO paramDTO = convert(eventParam);
        List<DicItem> alarm_event = dicItemMapper.queryDicItemListByDicCode("alarm_event");
        List<TableDataVo> list = alarmInfoMapper.staticTable(paramDTO);
        Map<String,List<TableDataVo>> dayEventMap = list.stream()
                .collect(Collectors.groupingBy(TableDataVo::getTime));
        Map<String,Integer> eventNumberMap = new HashMap<>();
        for (Map.Entry<String, List<TableDataVo>> entry : dayEventMap.entrySet()) {
            List<TableDataVo> dayData = entry.getValue();
            Map<String, TableDataVo> eventCodeMap = dayData.stream().collect(Collectors.toMap(p -> p.getEventCode(),
                    Function.identity(), (key1, key2) -> key2));
            for (DicItem item : alarm_event) {
                TableDataVo vo = new TableDataVo();
                vo.setTime(entry.getKey());
                TableDataVo dataVo = eventCodeMap.get(item.getItemCode());
                vo.setEventName(item.getItemName());
                vo.setEventCode(item.getItemCode());
                if (ObjectUtil.isNotNull(dataVo)) {
                    vo.setTotal(dataVo.getTotal());
                } else {
                    vo.setTotal(0);
                }
                Integer number = eventNumberMap.get(item.getItemCode());
                if (number != null) {
                    eventNumberMap.put(item.getItemCode(), vo.getTotal() + number);
                } else {
                    eventNumberMap.put(item.getItemCode(), 0);
                }
                result.add(vo);
            }
        }
        result = result.stream().sorted(Comparator.comparing(TableDataVo::getTime)).collect(Collectors.toList());
        if (result != null && result.size() > 0) {
            for (DicItem item : alarm_event) {
                TableDataVo vo = new TableDataVo();
                vo.setTime("平均");
                Integer total = eventNumberMap.get(item.getItemCode());
                vo.setEventName(item.getItemName());
                vo.setEventCode(item.getItemCode());
                vo.setTotal(total / dayEventMap.keySet().size());
                result.add(vo);
            }
        }
        return result;
    }

    @Override
    public void export(ExportParam eventParam, HttpServletResponse response) throws Exception {
        List<DicItem> alarm_event = dicItemMapper.queryDicItemListByDicCode("alarm_event");

        Document document = new Document();
        DocumentBuilder builder = new DocumentBuilder(document);
        // 设置页面横向布局
        builder.getPageSetup().setOrientation(Orientation.LANDSCAPE);

        // 图片设置
        String fileContent = eventParam.getFile();
        String[] base = fileContent.split(",");
        byte[] imageByte = Base64.decode(base[base.length - 1]);
        builder.insertImage(imageByte);


        Table table = builder.startTable();
        builder.insertCell();
        builder.write("日期");
        for (DicItem item : alarm_event) {
            builder.insertCell();
            builder.write(item.getItemName());
        }
        // 表头结束
        builder.endRow();

        List<TableDataVo> dataList = table(eventParam);
        List<List<Object>> excelData = new ArrayList<>();
        Map<String, List<TableDataVo>> mapData = dataList.stream().collect(Collectors.groupingBy(TableDataVo::getTime));
        mapData = mapData.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1,e2)->e1,LinkedHashMap::new));
        for (Map.Entry<String, List<TableDataVo>> entry : mapData.entrySet()) {
            List<TableDataVo> data = entry.getValue();
            List<Object> itemList = new ArrayList<>();
            itemList.add(entry.getKey());
            Map<String, TableDataVo> eventDataMap = data.stream().collect(Collectors.toMap(p -> p.getEventCode(),
                    Function.identity(), (key1, key2) -> key2));
            for (DicItem item : alarm_event) {
                TableDataVo vo = eventDataMap.get(item.getItemCode());
                itemList.add(vo.getTotal());
            }
            excelData.add(itemList);
        }

        // 写入数据
        builder.getRowFormat().setHeight(20);
        builder.getRowFormat().setHeightRule(HeightRule.EXACTLY);
        for (List<Object> row : excelData) {
            for (Object o:row) {
                builder.insertCell();
                builder.write(String.valueOf(o));
            }
            builder.endRow();
        }
        builder.endTable();

        // 设置表格列宽
        for (Row row : table.getRows()) {
            for (Cell cell : row.getCells()) {
                // 列宽
                cell.getCellFormat().setWidth(115);
                //自动换行
                cell.getCellFormat().setWrapText(true);
            }
        }
        String fileName = UUID.randomUUID().toString().replace("-","");
        String pdfPath = System.getProperty("user.dir") + File.separator + fileName + ".pdf";
        document.save(pdfPath,SaveFormat.PDF);
        File file = new File(pdfPath);
        if(file.exists()){
            response.setHeader("content-Type", "application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".pdf");
            FileInputStream inputStream = new FileInputStream(file);
            byte [] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes))>0){
                response.getOutputStream().write(bytes,0,len);
            }
            IOUtils.closeQuietly(inputStream);
        }
    }

    @Override
    public List<DicItem> selectEventItem(String eventCode) {
        return dicItemMapper.queryDicItemListByDicCode(eventCode);
    }

    private ParamDTO convert(EventParam param) {
        ParamDTO paramDTO = new ParamDTO();
        paramDTO.setEventSource(param.getEventSource());
        paramDTO.setType(param.getType());
        if (StringUtils.isNotBlank(param.getBeginTime())) {
            paramDTO.setBeginTime(DateUtil.parseDate(param.getBeginTime()));
        }
        if (StringUtils.isNotBlank(param.getEndTime())) {
            Date time = DateUtil.parseDate(param.getEndTime());
            String endTimeStr = DateUtil.formatDate(time) + " 23:59:59";
            paramDTO.setEndTime(DateUtil.parseDateTime(endTimeStr));
        }
        return paramDTO;
    }
}
