package com.xtxk.cn.dto.statistic.event;

import lombok.Data;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-07 20:39
 */
@Data
public class EventTypeHandleVo implements Serializable {

    private static final long serialVersionUID = 3822541965066352625L;

    private String eventName;
    private String eventCode;
    private Integer total;
    private Integer number;
}
