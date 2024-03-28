package com.xtxk.cn.third.service.impl;

import com.xtxk.cn.third.entity.gate.GateMachine;
import com.xtxk.cn.third.mapper.GateMachineMapper;
import com.xtxk.cn.third.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-04 18:32
 */
@Service
public class AccessServiceImpl implements AccessService {

    @Autowired
    private GateMachineMapper gateMachineMapper;


    @Override
    public Boolean pullAccessControlInfo(Integer page, Integer size) {
        return null;
    }


    @Override
    public Boolean syncAccessControlInfo() {
        List<GateMachine> list = gateMachineMapper.selectList();


        return null;
    }


}
