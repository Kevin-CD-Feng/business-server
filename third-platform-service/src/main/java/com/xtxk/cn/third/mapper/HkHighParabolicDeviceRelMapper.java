package com.xtxk.cn.third.mapper;

import com.xtxk.cn.third.entity.hk.HkHighParabolicDeviceRel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface HkHighParabolicDeviceRelMapper {

    int deleteByPrimaryKey(String hkDeviceId);

    int insert(HkHighParabolicDeviceRel record);

    int insertSelective(HkHighParabolicDeviceRel record);

    HkHighParabolicDeviceRel selectByPrimaryKey(String hkDeviceId);

    int updateByPrimaryKeySelective(HkHighParabolicDeviceRel record);

    int updateByPrimaryKey(HkHighParabolicDeviceRel record);

    List<HkHighParabolicDeviceRel> selectList();

}