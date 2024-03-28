package com.xtxk.cn.dto.statistic.event;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-11 10:26
 */
@Data
public class ParamDTO implements Serializable {

    private static final long serialVersionUID = 7824287632903827104L;
    private Integer eventSource;

    private Date beginTime;

    private Date endTime;

    private Integer type;
}
