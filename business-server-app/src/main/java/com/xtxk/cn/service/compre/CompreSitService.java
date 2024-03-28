package com.xtxk.cn.service.compre;

import com.xtxk.cn.entity.HousingEstateAreaPO;
import com.xtxk.cn.entity.HousingEstateSitDto;

/**
 * CompreSitService
 *
 * @author chenzhi
 * @date 2022/10/19 10:24
 * @description
 */
public interface CompreSitService {

    /**
     * 获取小区面积概况
     * @return
     */
    HousingEstateSitDto getHousingEstateSit();
}
