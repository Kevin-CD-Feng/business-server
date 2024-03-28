package com.xtxk.recognition.prepare.service.svc;


import com.xtxk.recognition.prepare.service.dto.alarmDefine.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AlarmDefineService {

    /**
     * 新增告警事件
     * @param addAlarmDefineReqDto
     */
    void add(AddAlarmDefineReqDto addAlarmDefineReqDto);

    /**
     * 查询告警事件详情
     * @param id
     * @return
     */
    DetailAlarmDefineRespDto detail(Integer id);

    /**
     * 更新
     * @param updateAlarmDefineReqDto
     */
    void update(UpdateAlarmDefineReqDto updateAlarmDefineReqDto);

    /**
     * 删除告警事件
     * @param id
     */
    void delete(Integer[] id);

    /**
     * 列表查询
     * @param pageAlarmDefineReqDto
     * @return
     */
    PageAlarmDefineRespDto pageList(PageAlarmDefineReqDto pageAlarmDefineReqDto);

    /**
     * 校验告警事件是否存在
     * @param eventCode
     * @return
     */
    void checkEvent(String eventCode);

    /**
     * 查询事件类型列表
     * @return
     */
    List<Map<String,Object>> queryEventTypeList();


    /**
     * 根据type查询对应事件
     * @param eventType
     * @return
     */
    List<Map<String,Object>> type2Event(String eventType);


    /**
     * 处理告警信息
     * @param dealAlarmInfoReqDto
     */
    void dealAlarmInfo(PushAlarmInfoReqDto dealAlarmInfoReqDto) throws IOException;


    Boolean canPushAlarm(CanPushAlarmReqDto canPushAlarmReqDto);

    HashMap<String,Integer> getVioResource(CanPushAlarmReqDto canPushAlarmReqDto);
}