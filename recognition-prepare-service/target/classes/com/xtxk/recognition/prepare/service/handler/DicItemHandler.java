package com.xtxk.recognition.prepare.service.handler;

import com.xtxk.recognition.prepare.service.entity.DicItem;
import com.xtxk.recognition.prepare.service.mapper.DicItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
