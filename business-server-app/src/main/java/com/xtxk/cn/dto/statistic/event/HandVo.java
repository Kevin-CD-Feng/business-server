package com.xtxk.cn.dto.statistic.event;

import lombok.Data;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-11 9:57
 */
@Data
public class HandVo implements Serializable {
    private static final long serialVersionUID = -2393964074457163977L;
    private String time;
    private Integer total;
}
