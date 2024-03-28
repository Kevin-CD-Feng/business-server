package com.xtxk.cn.mapper;

import com.xtxk.cn.dto.carInfo.ResourceCntWithTypeDto;
import com.xtxk.cn.dto.carInfo.SearchCarDTO;
import com.xtxk.cn.entity.CarInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CarInfoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param carId 主键
     * @return 实例对象
     */
    CarInfo queryById(Integer carId);

    /**
     * 查询指定行数据
     *
     * @param searchCarDTO 查询条件
     * @return 对象列表
     */
    List<CarInfo> findByPage(SearchCarDTO searchCarDTO);

    /**
     * 统计总行数
     *
     * @param carInfo 查询条件
     * @return 总行数
     */
    long count(CarInfo carInfo);

    /**
     * 新增数据
     *
     * @param carInfo 实例对象
     * @return 影响行数
     */
    int insert(CarInfo carInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<CarInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<CarInfo> entities);


    /**
     * 修改数据
     *
     * @param carInfo 实例对象
     * @return 影响行数
     */
    int update(CarInfo carInfo);

    /**
     * 通过主键删除数据
     *
     * @param carId 主键
     * @return 影响行数
     */
    int deleteById(Integer carId);

    /***
     * 批量删除车辆信息
     * @param ids
     * @return
     */
    int deleteBatch(@Param("ids") List<Integer> ids);

    List<ResourceCntWithTypeDto> queryCarCntWithType();
}

