package com.xtxk.cn.service.impl.dic;

import com.xtxk.cn.entity.DicItem;
import com.xtxk.cn.mapper.DicItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DicItemHandler
 *
 * @author chenzhi
 * @date 2022/11/7 11:19
 * @description
 */
@Component
public class DicItemHandler {

    @Autowired
    private DicItemMapper dicItemMapper;

    private Map<String, String> map;

    public DicItemHandler() {
        map = new HashMap<>();
    }


    public Map<String, String> getDicItemCodeMapping() {
        map.clear();
        List<DicItem> list = dicItemMapper.getAll();
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(e -> {
                map.put(e.getItemCode(), e.getItemName());
            });
        }
        return map;
    }

    public List<DicItem> getAllDicItemByDicCode(String dicCode) {
        return dicItemMapper.queryDicItemListByDicCode(dicCode);
    }
}
