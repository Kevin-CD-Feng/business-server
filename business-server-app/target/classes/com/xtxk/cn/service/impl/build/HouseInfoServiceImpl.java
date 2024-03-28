package com.xtxk.cn.service.impl.build;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xtxk.cn.common.PageRspDto;
import com.xtxk.cn.dto.build.HouseParams;
import com.xtxk.cn.dto.build.HouseVo;
import com.xtxk.cn.mapper.HouseInfoMapper;
import com.xtxk.cn.service.build.HouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/***
 * @description 出租屋相关
 * @author liulei
 * @date 2023-09-01 10:21
 */
@Service
public class HouseInfoServiceImpl implements HouseInfoService {

    @Autowired
    private HouseInfoMapper houseInfoMapper;


    @Override
    public PageRspDto buildingInfoList(HouseParams pageParam) {
        PageRspDto page = new PageRspDto();
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        PageInfo pageInfo = new PageInfo(houseInfoMapper.selectPageList(pageParam));
        page.setList(pageInfo.getList());
        page.setCount(pageInfo.getTotal());
        page.setPageCount((int) pageInfo.getTotal());
        return page;
    }


    @Override
    public List<HouseVo> export(HouseParams houseParams) {
        return houseInfoMapper.selectPageList(houseParams);
    }

}
