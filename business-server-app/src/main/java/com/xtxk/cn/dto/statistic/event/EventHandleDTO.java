package com.xtxk.cn.dto.statistic.event;

import lombok.Data;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-07 20:23
 */
@Data
public class EventHandleDTO implements Serializable {

    private static final long serialVersionUID = -8017232253116943437L;
    private String eventCode;
    private Integer total;
    private Integer number;

}
