package com.xtxk.cn.mapper;

import com.xtxk.cn.dto.dic.EventDictItemDto;
import com.xtxk.cn.entity.DicItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典项Mapper
 */
@Mapper
public interface DicItemMapper {


    /**
     * 批量新增字典项
     * @param dicItems
     */
    void batchAdd(List<DicItem> dicItems);

    /**
     * 校验字典项code是否存在
     * @param itemCode
     * @return
     */
    String checkItemCodeUnique(@Param("itemCode") String itemCode);

    /**
     * 根据dicCode查询字典项列表
     * @param dicCode
     * @return
     */
    List<DicItem> queryDicItemList(@Param("dicCode") String dicCode);

    /**
     * 根据itemParentCode查询字典项列表
     * @param itemParentCode
     * @return
     */
    List<DicItem> queryByItemParentCode(@Param("itemParentCode") String itemParentCode);

    /**
     * 根据itemCode查询itemParentCode
     * @param itemCode
     * @return
     */
    String queryItemParentCodeByItemCode(@Param("itemCode") String itemCode);

    /**
     * 根据itemCode查询itemName
     * @param itemCode
     * @return
     */
    String queryItemNameByItemCode(@Param("itemCode") String itemCode);

    /**
     * 根据字典元素id查询字典项列表
     * @param dicId
     * @return
     */
    List<DicItem> queryDicItemListByDicId(@Param("dicId") Integer dicId);

    /**
     * 根据字典元素dic_code查询字典项列表
     * @param dicCode
     * @return
     */
    List<DicItem> queryDicItemListByDicCode(@Param("dicCode") String dicCode);

    /**
     * 根据itemCodes查询字典项集合
     * @param itemCodes
     * @return
     */
    List<DicItem> queryDicItemListByItemCodes(@Param("itemCodes") List<String> itemCodes);

    /**
     * 逻辑删除
     * @param itemCodes
     */
    void deleteByItemCodes(@Param("itemCodes") List<String> itemCodes);

    /**
     * 批量更新
     * @param dicItems
     */
    void batchUpdateByIds(@Param("list") List<DicItem> dicItems);

    /**
     * 获取所有的code和name
     *
     * @return
     */
    List<DicItem> getAll();

    /**
     * 获取所有的EventDictItem
     * @return
     */
    List<EventDictItemDto> queryEventDictItem();

    /**
     * 根据事件类型查询告警类型
     * @param itemCode
     * @return
     */
   String getAlarmType(@Param("itemCode") String itemCode);
}
