package com.xtxk.cn.third.mapper;

import com.xtxk.cn.third.entity.user.PersonInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cursor.Cursor;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface PersonInfoMapper {

    int deleteByPrimaryKey(Integer personId);

    int insert(PersonInfo record);

    int insertSelective(PersonInfo record);

    PersonInfo selectByPrimaryKey(Integer personId);

    int updateByPrimaryKeySelective(PersonInfo record);

    int updateByPrimaryKey(PersonInfo record);

    /***
     * 查询用户信息
     * @return
     */
    List<PersonInfo> selectList();

    /***
     * 批量插入用户数据
     * @param list
     * @return
     */
    int insertBatchSave(@Param("list") List<PersonInfo> list);

    /***
     * 批量更新数据
     * @param list
     * @return
     */
    int updateBatchSave(@Param("list") List<PersonInfo> list);

    /***
     * 批量删除
     * @param list
     * @return
     */
    int deleteBatchSave(@Param("list") List<Integer> list);

    /***
     * 流式查询
     * @return
     */
    Cursor<PersonInfo> streamQueryList();
    
}