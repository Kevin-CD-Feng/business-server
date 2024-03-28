package com.xtxk.cn.mapper;

import com.xtxk.cn.dto.build.HouseParams;
import com.xtxk.cn.dto.build.HouseVo;
import com.xtxk.cn.entity.HouseInfo;
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
     * 查询房屋数据
     * @param houseParams
     * @return
     */
    List<HouseVo> selectPageList(@Param("houseParams") HouseParams houseParams);

}