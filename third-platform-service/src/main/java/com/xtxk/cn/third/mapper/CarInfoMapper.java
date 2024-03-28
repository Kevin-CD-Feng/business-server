package com.xtxk.cn.third.mapper;


import com.xtxk.cn.third.entity.car.CarInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cursor.Cursor;

import java.util.List;

@Mapper
public interface CarInfoMapper {

    int deleteByPrimaryKey(Integer carId);

    int insert(CarInfo record);

    int insertSelective(CarInfo record);

    CarInfo selectByPrimaryKey(Integer carId);

    int updateByPrimaryKeySelective(CarInfo record);

    int updateByPrimaryKey(CarInfo record);

    /***
     *
     * @return
     */
    List<CarInfo> selectList();

    /***
     * 批量插入车辆数据
     * @param list
     * @return
     */
    int insertBatchSave(@Param("list") List<CarInfo> list);

    /***
     * 批量更新车辆数据
     * @param list
     * @return
     */
    int updateBatchSave(@Param("list") List<CarInfo> list);

    /***
     * 批量删除车辆数据
     * @param list
     * @return
     */
    int deleteBatchSave(@Param("list") List<Integer> list);

    Cursor<CarInfo>  streamQueryCarList();
}