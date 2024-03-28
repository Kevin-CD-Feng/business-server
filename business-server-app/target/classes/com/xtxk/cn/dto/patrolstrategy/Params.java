package com.xtxk.cn.dto.patrolstrategy;

import lombok.Data;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-25 15:19
 */
@Data
public class Params implements Serializable {

    private static final long serialVersionUID = 1155058415827256705L;

    private String strategyName;
    private Integer strategyType;
}
