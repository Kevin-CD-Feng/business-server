package com.xtxk.recognition.prepare.service.mapper;

import com.xtxk.recognition.prepare.service.dto.dic.DicAddItemDto;
import com.xtxk.recognition.prepare.service.dto.dic.DicParentItemDto;
import com.xtxk.recognition.prepare.service.dto.dic.SuperiorItemDto;
import com.xtxk.recognition.prepare.service.entity.Dic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典mapper
 */
@Mapper
public interface DicMapper {

    /**
     * 查询字典列表
     * @return
     */
    List<DicParentItemDto> list(@Param("dicName") String dicName);

    /**
     * 字典元素详情
     * @param id
     * @return
     */
    Dic queryById(@Param("id") Integer id);

    /**
     * 根据id查询父字典元素名称
     * @param id
     * @return
     */
    String queryParentName(@Param("id") Integer id);

    /**
     * 根据字典元素id查询字典项
     * @param id
     * @return
     */
    List<DicAddItemDto> queryItemsByDicId(@Param("dicId") Integer id);

    /**
     * 查询下拉项
     * @return
     */
    List<SuperiorItemDto> superiorList();

    /**
     * 根据code查询id
     * @param dicCode
     * @return
     */
    Integer queryIdByCode(String dicCode);

    /**
     * 新增字典元素
     * @param dic
     */
    Integer add(Dic dic);

    /**
     * 根据code查询
     * @param dicCode
     */
    String checkCodeUnique(@Param("dicCode") String dicCode);

    /**
     * 根据dicCode查询dicName
     * @param dicCode
     * @return
     */
    String queryDicNameByDicCode(@Param("dicCode") String dicCode);

    /**
     * 更新字典元素信息
     * @param dic
     */
    void updateDic(Dic dic);
}
