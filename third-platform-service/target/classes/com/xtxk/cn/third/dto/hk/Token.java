package com.xtxk.cn.third.dto.hk;

import lombok.Data;

import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-23 10:02
 */
@Data
public class Token implements Serializable {
    private static final long serialVersionUID = -9083941666348845189L;
    private String access_token;
    private String token_type;
    private Integer expires_in;
}
