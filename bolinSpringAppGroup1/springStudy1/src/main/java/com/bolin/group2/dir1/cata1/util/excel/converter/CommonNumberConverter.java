package com.bolin.group2.dir1.cata1.util.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.bolin.group2.dir1.cata1.util.excel.StringPool;

import java.util.Objects;

/**
 * 通用的数字转换器，结合NumberFormat注解使用
 * @author jack.wen
 * @since 2023/10/20 13:41
 */
public class CommonNumberConverter implements Converter<Integer> {


    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) {
        Integer value = context.getValue();
        String valueStr;
        if (Objects.isNull(value)) {
            valueStr = StringPool.EMPTY;
        } else {
            String[] dictArr = context.getContentProperty().getNumberFormatProperty().getFormat().split(StringPool.COMMA);
            valueStr = dictArr[value];
        }
        return new WriteCellData<>(valueStr);
    }

}
