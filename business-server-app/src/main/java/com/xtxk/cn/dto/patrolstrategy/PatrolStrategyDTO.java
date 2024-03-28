package com.xtxk.cn.dto.patrolstrategy;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-25 14:23
 */
@Data
public class PatrolStrategyDTO implements Serializable {

    private static final long serialVersionUID = -1945523212891154791L;

    private String strategyId;
    @NotBlank(message = "方案名称不能为空")
    private String strategyName;
    private Integer strategyType;
    @NotNull(message = "巡岗间隔不能为空")
    private Integer patrolInterval;
    @NotNull(message = "窗口不能为空")
    private Integer windowLayout;
    @NotBlank(message = "巡查窗口不能为空")
    private String patrolScreen;
    @NotNull(message = "传入类型不能为空")
    private Integer type;
    private List<GroupDeviceDTO> groupDeviceDtoList;

}
