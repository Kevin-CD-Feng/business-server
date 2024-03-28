package com.xtxk.cn.third.common;

import lombok.Data;
import java.io.Serializable;

/**
 * Result
 *
 * @author chenzhi
 * @date 2022/11/2 14:23
 * @description
 */
@Data
public class Result implements Serializable {

    private Integer code;

    private String description;

}
