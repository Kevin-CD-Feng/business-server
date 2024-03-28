package com.xtxk.cn.service.geo;

import com.xtxk.cn.dto.geogra.GeograObjDto;

import java.util.List;

/**
 * GeograService
 *
 * @author chenzhi
 * @date 2022/10/27 9:58
 * @description
 */
public interface GeograService {

    /**
     * 获取地理信息结果
     * @param type
     * @return
     */
    List<GeograObjDto> getGeograObjResult(String type);
}
