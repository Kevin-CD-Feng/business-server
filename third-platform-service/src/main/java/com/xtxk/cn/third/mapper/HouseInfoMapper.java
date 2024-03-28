package com.xtxk.cn.third.mapper;

import com.xtxk.cn.third.entity.house.HouseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HouseInfoMapper {

    int deleteByPrimaryKey(Integer houseId);

    int insert(HouseInfo record);

    int insertSelective(HouseInfo record);

    HouseInfo selectByPrimaryKey(Integer houseId);

    int updateByPrimaryKeySelective(HouseInfo record);

    int updateByPrimaryKey(HouseInfo record);

    /***
     * 查询出租屋数据
     * @return
     */
    List<HouseInfo> selectList();

    /***
     * 批量删除房屋数据
     * @param list
     */
    int deleteBatchSave(@Param("list") List<Integer> list);

    /***
     * 批量插入房屋数据
     * @param list
     * @return
     */
    int insertBatchSave(@Param("list") List<HouseInfo> list);

    /***
     * 批量更新房屋数据
     * @param list
     * @return
     */
    int updateBatchSave(@Param("list") List<HouseInfo> list);
}