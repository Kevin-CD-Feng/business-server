package com.xtxk.cn.third.mapper;


import com.xtxk.cn.third.entity.user.OiPersonnelInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cursor.Cursor;
import java.util.List;

@Mapper
public interface OiPersonnelInformationMapper {

    int deleteByPrimaryKey(String personId);

    int insert(OiPersonnelInformation record);

    int insertSelective(OiPersonnelInformation record);

    OiPersonnelInformation selectByPrimaryKey(String personId);

    int updateByPrimaryKeySelective(OiPersonnelInformation record);

    int updateByPrimaryKey(OiPersonnelInformation record);

    /***
     * 查询在用的小区人员数据
     * @return
     */
    List<OiPersonnelInformation> selectPersonList();

    /***
     * 流式查询
     * @return
     */
    Cursor<OiPersonnelInformation> streamQueryList();
}