package com.xtxk.cn.service.statistic;

import com.xtxk.cn.dto.AlarmStatusCount;
import com.xtxk.cn.dto.statistic.event.*;
import com.xtxk.cn.entity.DicItem;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-0715:14
 */
public interface EventStatisticService {

    /***
     * 事件状态统计
     * @return
     */
    List<AlarmStatusCount> status(EventParam param);

    /***
     * 告警处理统计
     * @param eventParam
     * @return
     */
    List<HandVo> handle(EventParam eventParam);

    /***
     * 事件类型统计
     * @param eventParam
     * @return
     */
    List<EventTypeHandleVo> type(EventParam eventParam);

    /***
     * 列表数据
     * @param eventParam
     * @return
     */
    List<TableDataVo> table(EventParam eventParam);

    /***
     * 导出数据
     * @param eventParam
     * @param response
     */
    void export(ExportParam eventParam, HttpServletResponse response) throws  Exception;

    /***
     *
     * @param eventCode
     * @return
     */
    List<DicItem> selectEventItem(String eventCode);
}
