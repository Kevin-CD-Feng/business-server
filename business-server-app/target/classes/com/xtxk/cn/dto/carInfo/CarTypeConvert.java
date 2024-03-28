package com.xtxk.cn.dto.carInfo;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;

/**
 * @author HW
 */
public class CarTypeConvert implements Converter<Integer> {

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
            case "本地车辆":
                return 1;
            case "外来车辆":
                return 2;
            case "重点车辆":
                return 3;
            default:
                break;
        }
        return null;
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) throws Exception {
        Integer cellValue = context.getValue();
        switch (cellValue) {
            case 1:
                return new WriteCellData<>("本地车辆");
            case 2:
                return new WriteCellData<>("外来车辆");
            case 3:
                return new WriteCellData<>("重点车辆");
            default:
                break;
        }
        return new WriteCellData<>("");
    }
}
