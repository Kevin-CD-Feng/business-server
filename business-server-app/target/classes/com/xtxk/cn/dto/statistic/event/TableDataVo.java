package com.xtxk.cn.dto.statistic.event;

import lombok.Data;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-11 11:32
 */
@Data
public class TableDataVo implements Serializable {

    private static final long serialVersionUID = 1683937540155281052L;

    private String time;
    private String eventCode;
    private String eventName;
    private Integer total;
}
