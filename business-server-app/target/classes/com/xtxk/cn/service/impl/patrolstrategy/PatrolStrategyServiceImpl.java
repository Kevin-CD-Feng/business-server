package com.xtxk.cn.service.impl.patrolstrategy;

import com.xtxk.cn.dto.patrolstrategy.GroupDeviceDTO;
import com.xtxk.cn.dto.patrolstrategy.Params;
import com.xtxk.cn.dto.patrolstrategy.PatrolStrategyDTO;
import com.xtxk.cn.entity.PatrolStrategy;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.mapper.GroupDeviceMapper;
import com.xtxk.cn.mapper.PatrolStrategyMapper;
import com.xtxk.cn.service.patrolstrategy.PatrolStrategyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-25 14:19
 */
@Service
public class PatrolStrategyServiceImpl implements PatrolStrategyService {

    @Autowired
    private PatrolStrategyMapper strategyMapper;
    @Autowired
    private GroupDeviceMapper groupDeviceMapper;

    /***
     * 添加轮巡方案
     * @param patrolStrategyDTO
     * @return
     */
    @Override
    public Boolean add(PatrolStrategyDTO patrolStrategyDTO) {
        String strategyName = patrolStrategyDTO.getStrategyName();
        PatrolStrategy strategy = strategyMapper.queryByStrategyName(strategyName);
        if (strategy != null) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, strategyName + "已存在,请重新输入轮巡方案名称！");
        }
        String strategyId = UUID.randomUUID().toString();
        strategy = new PatrolStrategy();
        strategy.setStrategyId(strategyId);
        strategy.setStrategyName(strategyName);
        strategy.setStrategyType(patrolStrategyDTO.getStrategyType());
        strategy.setPatrolScreen(patrolStrategyDTO.getPatrolScreen());
        strategy.setPatrolInterval(patrolStrategyDTO.getPatrolInterval());
        strategy.setType(patrolStrategyDTO.getType());
        strategy.setWindowLayout(patrolStrategyDTO.getWindowLayout());
        strategy.setCreateTime(new Date());
        strategy.setIsDel(0);
        strategy.setUpdateTime(new Date());
        strategyMapper.insertSelective(strategy);

        List<GroupDeviceDTO> groupDeviceList = patrolStrategyDTO.getGroupDeviceDtoList();
        for (GroupDeviceDTO groupDeviceDto : groupDeviceList) {
            groupDeviceDto.setGroupId(UUID.randomUUID().toString());
            if (!groupDeviceDto.getDeviceId().equals(groupDeviceDto.getPDeviceId())) {
                //通道设备ID及父设备ID
                groupDeviceDto.setDeviceId(StringUtils.isEmpty(groupDeviceDto.getPDeviceId()) ?
                        groupDeviceDto.getDeviceId() : groupDeviceDto.getDeviceId() + "," + groupDeviceDto.getPDeviceId());
            }
            groupDeviceDto.setStrategyId(strategyId);
        }
        return groupDeviceMapper.insertBatch(groupDeviceList) > 0;
    }

    /**
     * 轮巡方案编辑
     *
     * @param patrolStrategyDTO
     * @return
     */
    @Override
    public Boolean update(PatrolStrategyDTO patrolStrategyDTO) {
        String strategyId = patrolStrategyDTO.getStrategyId();
        PatrolStrategy strategy = strategyMapper.selectByPrimaryKey(strategyId);
        if (strategy == null) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, patrolStrategyDTO.getStrategyName() + "不存在,请重新输入轮巡方案名称！");
        }
        strategy.setStrategyName(patrolStrategyDTO.getStrategyName());
        strategy.setStrategyType(patrolStrategyDTO.getStrategyType());
        strategy.setPatrolInterval(patrolStrategyDTO.getPatrolInterval());
        strategy.setPatrolScreen(patrolStrategyDTO.getPatrolScreen());
        strategy.setType(patrolStrategyDTO.getType());
        strategy.setWindowLayout(patrolStrategyDTO.getWindowLayout());
        strategy.setIsDel(0);
        strategy.setUpdateTime(new Date());
        strategyMapper.updateByPrimaryKeySelective(strategy);

        List<GroupDeviceDTO> groupDeviceList = patrolStrategyDTO.getGroupDeviceDtoList();
        for (GroupDeviceDTO groupDeviceDto : groupDeviceList) {
            groupDeviceDto.setGroupId(UUID.randomUUID().toString());
            if (!groupDeviceDto.getDeviceId().equals(groupDeviceDto.getPDeviceId())) {
                //通道设备ID及父设备ID
                groupDeviceDto.setDeviceId(StringUtils.isEmpty(groupDeviceDto.getPDeviceId()) ?
                        groupDeviceDto.getDeviceId() : groupDeviceDto.getDeviceId() + "," + groupDeviceDto.getPDeviceId());
            }
            groupDeviceDto.setStrategyId(strategyId);
        }
        groupDeviceMapper.deleteByStrategyId(strategy.getStrategyId());
        return groupDeviceMapper.insertBatch(groupDeviceList) > 0;
    }

    /**
     * 轮巡方案删除
     *
     * @param strategyId
     * @return
     */
    @Override
    public Boolean delete(String strategyId) {
        PatrolStrategy strategy = strategyMapper.selectByPrimaryKey(strategyId);
        if (strategy == null) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, "轮巡方案不存在,删除轮巡方案名称失败！");
        }
        strategy.setIsDel(1);
        strategy.setUpdateTime(new Date());
        return strategyMapper.updateByPrimaryKeySelective(strategy) > 0;
    }

    /***
     * 查询轮巡方案列表
     * @param params
     * @return
     */
    @Override
    public List<PatrolStrategyDTO> queryList(Params params) {
        List<PatrolStrategyDTO> patrolStrategyDtoList = strategyMapper.queryList(params);
        List<GroupDeviceDTO> deviceGroupList = groupDeviceMapper.selectAll();
        Map<String, List<GroupDeviceDTO>> strategyMapList = deviceGroupList.stream().filter(g -> StringUtils.isNotBlank(g.getStrategyId()))
                .collect(Collectors.groupingBy(GroupDeviceDTO::getStrategyId));
        for (PatrolStrategyDTO dto : patrolStrategyDtoList) {
            List<GroupDeviceDTO> deviceList = strategyMapList.get(dto.getStrategyId());
            if (deviceList != null && deviceList.size() > 0) {
                for (GroupDeviceDTO deviceDto : deviceList) {
                    String[] array = deviceDto.getDeviceId().split(",");
                    deviceDto.setDeviceId((array.length > 1 ? array[0] : deviceDto.getDeviceId()));
                    deviceDto.setPDeviceId((array.length > 1 ? array[array.length - 1] : deviceDto.getDeviceId()));
                }
            }
            dto.setGroupDeviceDtoList(deviceList);
        }
        return patrolStrategyDtoList;
    }


}
