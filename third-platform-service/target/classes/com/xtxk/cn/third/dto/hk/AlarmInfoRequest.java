package com.xtxk.cn.third.dto.hk;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-23 14:53
 */
@Data
public class AlarmInfoRequest implements Serializable {

    private static final long serialVersionUID = -9222955711340046205L;

    /***
     * 设备id
     */
    private String resourceId;
    /***
     * 事件code
     */
    private String eventCode;
    /***
     * roi区域
     */
    private Integer[][] roi;
    /***
     * 告警时间
     */
    private String catchTime;
    /***
     * 图片路径
     */
    private String path;
    /***
     * 告警数据
     */
    private String data;

}
