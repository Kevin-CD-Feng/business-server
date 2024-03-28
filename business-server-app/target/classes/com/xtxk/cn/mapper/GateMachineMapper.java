package com.xtxk.cn.mapper;

import com.xtxk.cn.dto.geogra.GeograObjDto;
import com.xtxk.cn.entity.GateMachinePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface GateMachineMapper {

    /**
     * 通过关键字查询
     * @param keyWord
     * @return
     */
    List<GateMachinePO> queryByKeyWord(@Param("keyWord") String keyWord);

    /**
     * 新增
     * @param gateMachinePO
     */
    void add(GateMachinePO gateMachinePO);

    /**
     * 修改
     * @param gateMachinePO
     */
    void update(GateMachinePO gateMachinePO);

    /**
     * 通过主键查询
     * @param gateMachineId
     * @return
     */
    GateMachinePO getById(@Param("gateMachineId") Integer gateMachineId);

    /**
     * 删除
     * @param gateMachineId
     */
    void deleteById(@Param("gateMachineId") Integer gateMachineId);

    /**
     * 获取地理信息结果
     * @return
     */
    List<GeograObjDto> getGeograObjDto();

}
