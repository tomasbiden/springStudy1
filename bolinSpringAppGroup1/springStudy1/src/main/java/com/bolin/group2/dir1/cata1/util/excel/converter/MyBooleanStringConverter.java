package com.bolin.group2.dir1.cata1.util.excel.converter;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class MyBooleanStringConverter implements Converter<Boolean> {
    @Override
    public Class<Boolean> supportJavaTypeKey() {
        return Boolean.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 这里读的时候会调用
     *
     * @param cellData            NotNull
     * @param contentProperty     Nullable
     * @param globalConfiguration NotNull
     * @return
     */
    @Override
    public Boolean convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                     GlobalConfiguration globalConfiguration) {
        String value = cellData.getStringValue();
        if (StrUtil.isBlank(value)) {
            return null;
        }
        if ("是".equals(value)) {
            return Boolean.TRUE;
        }
        if ("否".equals(value)) {
            return Boolean.FALSE;
        }
        return null;
    }

    /**
     * 这里是写的时候会调用
     *
     * @param value               NotNull
     * @param contentProperty     Nullable
     * @param globalConfiguration NotNull
     * @return
     */
    @Override
    public WriteCellData<String> convertToExcelData(Boolean value, ExcelContentProperty contentProperty,
                                                    GlobalConfiguration globalConfiguration) {
        if (BooleanUtil.isTrue(value)) {
            return new WriteCellData<>("是");
        }
        if (BooleanUtil.isFalse(value)) {
            return new WriteCellData<>("否");
        }
        return new WriteCellData<>();
    }

}