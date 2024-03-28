package com.xtxk.cn.third.dto.hk;

import lombok.Data;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-01 18:23
 */
@Data
public class EventMessage implements Serializable {

    private static final long serialVersionUID = 4506518163753190750L;

    private String resourceId;
    private String imgUrl;
    private String time;

}
