package com.xtxk.cn.dto.alarmInfo;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.xtxk.cn.enums.AlarmStatusEnum;

import java.util.Objects;

/**
 * DisposalTypeConvert
 *
 * @author chenzhi
 * @date 2022/10/21 9:12
 * @description
 */
public class AlarmStatusConvert implements Converter<Integer> {

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public Integer convertToJavaData(ReadConverterContext<?> context) {
        String cellStr = context.getReadCellData().getStringValue();
        return AlarmStatusEnum.queryCodeByDesc(cellStr);
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) {
        Integer cellValue = context.getValue();
        return new WriteCellData<>(Objects.requireNonNull(AlarmStatusEnum.queryDescByCode(cellValue)));
    }
}
