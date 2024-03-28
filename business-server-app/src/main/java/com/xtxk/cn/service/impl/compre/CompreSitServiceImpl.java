package com.xtxk.cn.service.impl.compre;

import com.xtxk.cn.entity.BuildingCntDto;
import com.xtxk.cn.entity.HousingEstateAreaPO;
import com.xtxk.cn.entity.HousingEstateSitDto;
import com.xtxk.cn.entity.PerCarDevCount;
import com.xtxk.cn.mapper.HousingEstateAreaMapper;
import com.xtxk.cn.mapper.MonitorDeviceMapper;
import com.xtxk.cn.service.compre.CompreSitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CompreSitServiceImpl
 *
 * @author chenzhi
 * @date 2022/10/19 10:25
 * @description
 */
@Service
public class CompreSitServiceImpl implements CompreSitService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompreSitServiceImpl.class);

    @Autowired
    private HousingEstateAreaMapper housingEstateAreaMapper;

    @Autowired
    private MonitorDeviceMapper monitorDeviceMapper;

    @Override
    public HousingEstateSitDto getHousingEstateSit() {
        HousingEstateSitDto dto = new HousingEstateSitDto();
        HousingEstateAreaPO heaPO = housingEstateAreaMapper.getAll();
        PerCarDevCount pcdCount = monitorDeviceMapper.getPerCarDevCount();
        BuildingCntDto buildingCntAndDoorCnt = housingEstateAreaMapper.getBuildingCntAndDoorCnt();
        dto.setHousingEstateAreaPO(heaPO);
        dto.setPerCarDevCount(pcdCount);
        dto.setBuildingCntDto(buildingCntAndDoorCnt);
        return dto;
    }
}
