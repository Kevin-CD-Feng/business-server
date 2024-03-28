package com.xtxk.cn.dto.video;

import lombok.Data;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-09 14:42
 */
@Data
public class VideoParam implements Serializable {

    private static final long serialVersionUID = -7284592058824033354L;
    private String resourceId;
    private String resourceName;
    private String resourceSipId;
}
