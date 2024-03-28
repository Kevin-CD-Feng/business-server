package com.xtxk.cn.service.gatemachine;

import com.github.pagehelper.PageInfo;
import com.xtxk.cn.dto.geogra.AddGateMachineReqDto;
import com.xtxk.cn.dto.geogra.UpdateGateMachineReqDto;
import com.xtxk.cn.entity.GateMachinePO;

import java.util.List;

/**
 * GateMachineService
 *
 * @author chenzhi
 * @date 2022/10/14 15:08
 * @description
 */
public interface GateMachineService {

    /**
     * 分页查询门禁闸机列表
     * @param keyWord
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<GateMachinePO> getGateMachineList(String keyWord, Integer pageNum, Integer pageSize);

    /**
     * 新增
     * @param addGateMachineReqDto
     */
    void addGateMachine(AddGateMachineReqDto addGateMachineReqDto);

    /**
     * 修改
     * @param gateMachineId
     * @param updateGateMachineReqDto
     */
    void updateGateMachine(Integer gateMachineId, UpdateGateMachineReqDto updateGateMachineReqDto);

    /**
     * 删除
     * @param gateMachineId
     */
    void deleteGateMachine(Integer gateMachineId);

    /**
     * 获取需要导出的门禁闸机列表
     * @param keyWord
     * @return
     */
    List<GateMachinePO> getExportGateMachine(String keyWord);

    /**
     * 获取单个门禁闸机信息
     * @param gateMachineId
     * @return
     */
    GateMachinePO getGateMachine(Integer gateMachineId);
}
