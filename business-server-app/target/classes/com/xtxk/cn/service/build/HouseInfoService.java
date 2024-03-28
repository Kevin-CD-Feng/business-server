package com.xtxk.cn.service.build;

import com.xtxk.cn.common.PageRspDto;
import com.xtxk.cn.dto.build.HouseParams;
import com.xtxk.cn.dto.build.HouseVo;
import java.util.List;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-0110:21
 */
public interface HouseInfoService {


    /***
     *
     * @param houseParams
     * @return
     */
    PageRspDto buildingInfoList(HouseParams houseParams);

    /***
     * 导出房屋数据
     * @param houseParams
     * @return
     */
    List<HouseVo> export(HouseParams houseParams);
}
