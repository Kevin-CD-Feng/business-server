package com.xtxk.cn.mapper;

import com.xtxk.cn.dto.alarmInfo.PropertyPersonItemDto;
import com.xtxk.cn.dto.carInfo.CarFlowRspDto;
import com.xtxk.cn.dto.carInfo.CarFlowVo;
import com.xtxk.cn.dto.carInfo.ResourceCntWithTypeDto;
import com.xtxk.cn.dto.personInfo.*;
import com.xtxk.cn.entity.PersonInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


@Mapper
public interface PersonInfoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param personId 主键
     * @return 实例对象
     */
    PersonInfo queryById(Integer personId);

    /**
     * 查询指定行数据
     *
     * @param searchInfoDTO 查询条件
     * @return 对象列表
     */
    List<PersonInfo> queryPage(SearchInfoDTO searchInfoDTO);

    /**
     * 统计总行数
     *
     * @param personInfo 查询条件
     * @return 总行数
     */
    long count(PersonInfo personInfo);

    /**
     * 新增数据
     *
     * @param personInfo 实例对象
     * @return 影响行数
     */
    int insert(PersonInfo personInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<PersonInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<PersonInfo> entities);


    /**
     * 修改数据
     *
     * @param personInfo 实例对象
     * @return 影响行数
     */
    int update(PersonInfo personInfo);

    /**
     * 通过主键删除数据
     *
     * @param personId 主键
     * @return 影响行数
     */
    int deleteById(Integer personId);

    /***
     * 批量删除
     * @param ids
     * @return
     */
    int deleteBatch(@Param("ids") List<Integer> ids);


    /**
     * 查询物业人员列表
     * @return
     */
    List<PropertyPersonItemDto> queryPropertyPerson();

    /**
     * 通过身份证查询
     * @param idNumber
     * @return
     */
    PersonInfo getByIdNumber(String idNumber);

    /**
     * 根据ids批量查询
     * @param ids
     * @return
     */
    List<PersonInfo> queryByIds(@Param("ids") List<Integer> ids);

    List<CarFlowVo>  queryCarFlow(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    List<PersonFlowVo>  queryPersonFlow(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    List<VioModelCntDto> queryVioCnt(@Param("type") Integer type,@Param("list") List<String> list);

    List<VioInfoDto> queryViolationInfoByTargetId(@Param("targetId") String targetId, @Param("type") Integer type);

    List<ResourceCntWithTypeDto> queryPersonCntWithType();
}

