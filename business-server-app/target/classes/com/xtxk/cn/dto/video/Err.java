package com.xtxk.cn.dto.video;

import lombok.Data;

import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-07 10:16
 */
@Data
public class Err implements Serializable {

    private Integer ErrorCode;
    private String  ErrorDescription;

}
