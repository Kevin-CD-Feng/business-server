package com.xtxk.cn.service.impl.gatemachine;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.xtxk.cn.dto.geogra.AddGateMachineReqDto;
import com.xtxk.cn.dto.geogra.UpdateGateMachineReqDto;
import com.xtxk.cn.entity.GateMachinePO;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.mapper.GateMachineMapper;
import com.xtxk.cn.service.gatemachine.GateMachineService;
import com.xtxk.cn.service.impl.dic.DicItemHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * GateMachineServiceImpl
 *
 * @author chenzhi
 * @date 2022/10/14 15:08
 * @description
 */
@Service
public class GateMachineServiceImpl implements GateMachineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GateMachineServiceImpl.class);

    @Autowired
    private GateMachineMapper gateMachineMapper;

    @Autowired
    private DicItemHandler dicItemHandler;


    @Override
    public PageInfo<GateMachinePO> getGateMachineList(String keyWord, Integer pageNum, Integer pageSize) {
        if(pageNum!=0 && pageSize!=0){
            PageMethod.startPage(pageNum, pageSize);
        }
        List<GateMachinePO> list = gateMachineMapper.queryByKeyWord(keyWord);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
            list.forEach(e -> {
                e.setDistrictName(mapping.get(e.getDistrict()));
            });
        }
        return new PageInfo<>(list);
    }

    @Override
    public void addGateMachine(AddGateMachineReqDto addGateMachineReqDto) {
        GateMachinePO po = new GateMachinePO();
        BeanUtils.copyProperties(addGateMachineReqDto, po);
        gateMachineMapper.add(po);
    }

    @Override
    public void updateGateMachine(Integer gateMachineId, UpdateGateMachineReqDto updateGateMachineReqDto) {
        GateMachinePO po = gateMachineMapper.getById(gateMachineId);
        if (null == po) {
            LOGGER.info("gateMachineId:{} is not exist", gateMachineId);
            throw new ServiceException(ErrorCode.GATEMACHINE_ID_NOT_EXIST);
        }
        BeanUtils.copyProperties(updateGateMachineReqDto, po);
        gateMachineMapper.update(po);
    }

    @Override
    public void deleteGateMachine(Integer gateMachineId) {
        GateMachinePO po = gateMachineMapper.getById(gateMachineId);
        if (null == po) {
            LOGGER.info("gateMachineId:{} is not exist", gateMachineId);
            throw new ServiceException(ErrorCode.GATEMACHINE_ID_NOT_EXIST);
        }
        gateMachineMapper.deleteById(gateMachineId);
    }

    @Override
    public List<GateMachinePO> getExportGateMachine(String keyWord) {
        List<GateMachinePO> list = gateMachineMapper.queryByKeyWord(keyWord);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
            list.forEach(e -> {
                e.setDistrictName(mapping.get(e.getDistrict()));
            });
        }
        return list;
    }

    @Override
    public GateMachinePO getGateMachine(Integer gateMachineId) {
        GateMachinePO po = gateMachineMapper.getById(gateMachineId);
        Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
        po.setDistrictName(mapping.get(po.getDistrict()));
        return po;
    }
}
