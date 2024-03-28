package com.xtxk.cn.dto.dic;

import java.util.List;

public class DictionaryEntryRespDto {

    private List<DictionaryEntryItemDto> dictionaryEntryItemDtos;

    public List<DictionaryEntryItemDto> getDictionaryEntryItemDtos() {
        return dictionaryEntryItemDtos;
    }

    public void setDictionaryEntryItemDtos(List<DictionaryEntryItemDto> dictionaryEntryItemDtos) {
        this.dictionaryEntryItemDtos = dictionaryEntryItemDtos;
    }
}
