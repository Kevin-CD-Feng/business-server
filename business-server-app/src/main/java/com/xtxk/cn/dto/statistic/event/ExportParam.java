package com.xtxk.cn.dto.statistic.event;

import lombok.Data;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-12 10:52
 */
@Data
public class ExportParam extends EventParam implements Serializable {

    private String file;
}
