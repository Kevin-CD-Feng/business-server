package com.xtxk.cn.mapper;

import com.xtxk.cn.entity.BuildingCntDto;
import com.xtxk.cn.entity.HousingEstateAreaPO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface HousingEstateAreaMapper {

    /**
     * 获取小区面积数据
     * @return
     */
    HousingEstateAreaPO getAll();

    BuildingCntDto getBuildingCntAndDoorCnt();
}
