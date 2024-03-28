package com.xtxk.cn.third.dto.hk;

import lombok.Data;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-23 10:01
 */
@Data
public class HkTokenRes implements Serializable {
    private static final long serialVersionUID = -1786466364527339595L;
    private String code;
    private String msg;
    private Token data;
}
