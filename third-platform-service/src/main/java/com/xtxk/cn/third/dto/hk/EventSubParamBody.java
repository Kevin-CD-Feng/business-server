package com.xtxk.cn.third.dto.hk;

import lombok.Data;
import java.io.Serializable;
import java.util.List;
/***
 * @description TODO
 * @author liulei
 * @date 2023-08-22 19:12
 */
@Data
public class EventSubParamBody implements Serializable {
    private static final long serialVersionUID = -7152460127431660410L;

    /***
     * 告警类型事件码
     */
    private List<Integer> eventTypes;
    /***
     * 回调地址
     */
    private String eventDest;
    /***
     * 订阅类型 0 订阅原始事件
     */
    private Integer subType;

}
