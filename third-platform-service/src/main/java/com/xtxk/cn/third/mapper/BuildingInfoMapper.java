package com.xtxk.cn.third.mapper;

import com.xtxk.cn.third.entity.build.BuildingInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 楼栋
 * @author Administrator
 */
@Mapper
public interface BuildingInfoMapper {

    int deleteByPrimaryKey(Integer buildingId);

    int insert(BuildingInfo record);

    int insertSelective(BuildingInfo record);

    BuildingInfo selectByPrimaryKey(Integer buildingId);

    int updateByPrimaryKeySelective(BuildingInfo record);

    int updateByPrimaryKey(BuildingInfo record);

    /***
     * 查询楼栋数据
     * @return
     */
    List<BuildingInfo> selectList();

    /****
     * 批量插入楼栋数据
     * @param list
     * @return
     */
    int insertBatchSave(@Param("list") List<BuildingInfo> list);

    /****
     * 批量更新楼栋数据
     * @param list
     * @return
     */
    int updateBatchSave(@Param("list") List<BuildingInfo> list);

    /***
     * 批量删除楼栋数据
     * @param list
     * @return
     */
    int deleteBatchSave(@Param("list") List<Integer> list);

    /***
     * 查询数据湖楼栋数据
     * @return
     */
    List<BuildingInfo> selectByLakeBuild();
}