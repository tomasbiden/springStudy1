package com.bolin.group2.dir1.cata1.util.excel.converter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class CanOrNotByteStringConverter implements Converter<Byte> {
    @Override
    public Class<Byte> supportJavaTypeKey() {
        return Byte.class;
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
    public Byte convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                     GlobalConfiguration globalConfiguration) {
        String value = cellData.getStringValue();
        if (StrUtil.isBlank(value)) {
            return null;
        }
        if ("能".equals(value)) {
            return 1;
        }
        if ("否".equals(value)) {
            return 0;
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
    public WriteCellData<String> convertToExcelData(Byte value, ExcelContentProperty contentProperty,
                                                    GlobalConfiguration globalConfiguration) {
        if (Byte.valueOf("1").equals(value)) {
            return new WriteCellData<>("能");
        }
        if (Byte.valueOf("0").equals(value)) {
            return new WriteCellData<>("否");
        }
        return new WriteCellData<>();
    }

}