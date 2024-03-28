package com.xtxk.cn.service.car;

import com.github.pagehelper.PageInfo;
import com.xtxk.cn.dto.carInfo.ResourceCntWithTypeDto;
import com.xtxk.cn.dto.carInfo.SearchCarDTO;
import com.xtxk.cn.entity.CarInfo;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * CarInfoService
 *
 * @author chenzhi
 * @date 2022/10/14 9:37
 * @description
 */
public interface CarInfoService {

    /***
     * 分页查询
     * @param searchCarDTO
     * @return
     */
    PageInfo findByPage(SearchCarDTO searchCarDTO);

    /***
     * 保存车辆档案信息
     * @param carInfo
     * @return
     */
    Boolean saveCarInfo(CarInfo carInfo);

    /***
     * 根据id删除车辆档案信息
     * @param id
     * @return
     */
    Boolean deleteById(Integer id);

    /***
     * 批量插入数据
     * @param list
     * @return
     */
    Boolean insertBatch(List<CarInfo> list);

    /***
     * 批量删除车辆信息
     * @param ids
     * @return
     */
    Boolean deleteBatch(String ids);


    List<ResourceCntWithTypeDto> queryCarCntWithType();

}
