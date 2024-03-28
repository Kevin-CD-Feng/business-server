package com.xtxk.cn.dto.personInfo;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;

/**
 * @author HW
 */
public class PerSonTypeConvert implements Converter<Integer> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(ReadConverterContext<?> context) throws Exception {
        String cellStr = context.getReadCellData().getStringValue();
        switch (cellStr) {
            case "本地人员":
                return 1;
            case "外来人员":
                return 2;
            case "物业人员":
                return 3;
            default:
                break;
        }
        return null;
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) throws Exception {
        //对象属性转CellData
        Integer cellValue = context.getValue();
        switch (cellValue) {
            case 1:
                return new WriteCellData<>("本地人员");
            case 2:
                return new WriteCellData<>("外来人员");
            case 3:
                return new WriteCellData<>("物业人员");
            default:
                break;
        }
        return new WriteCellData<>("");
    }
}
