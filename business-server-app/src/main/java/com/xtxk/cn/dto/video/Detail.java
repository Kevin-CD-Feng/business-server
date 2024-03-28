package com.xtxk.cn.dto.video;

import lombok.Data;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-07 10:17
 */
@Data
public class Detail implements Serializable {

    private String userID;
    private String resourceId;
    private String status;
    private String currentTime;


}
