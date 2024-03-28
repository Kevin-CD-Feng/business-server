package com.xtxk.cn.service.patrolstrategy;

import com.xtxk.cn.dto.patrolstrategy.Params;
import com.xtxk.cn.dto.patrolstrategy.PatrolStrategyDTO;

import java.util.List;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-25 14:19
 */
public interface PatrolStrategyService {

    /***
     * 添加轮巡方案
     * @param patrolStrategyDTO
     * @return
     */
    Boolean add(PatrolStrategyDTO patrolStrategyDTO);

    /**
     * 轮巡方案编辑
     * @param patrolStrategyDTO
     * @return
     */
    Boolean update(PatrolStrategyDTO patrolStrategyDTO);

    /**
     * 轮巡方案删除
     * @param strategyId
     * @return
     */
    Boolean delete(String strategyId);

    /***
     * 查询轮巡方案列表
     * @param params
     * @return
     */
    List<PatrolStrategyDTO> queryList(Params params);
}
