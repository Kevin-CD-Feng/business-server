package com.xtxk.cn.dto.video;

import lombok.Data;

import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-07 10:15
 */
@Data
public class VideoReq implements Serializable {

    private Err err;
    private Detail detail;

}
