package com.xtxk.cn.dto.video;

import lombok.Data;

import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-07 11:38
 */
@Data
public class ResponseResult implements Serializable {

    private String responseDesc;
    private String responseCode;
    private Boolean isSuccess;
}
