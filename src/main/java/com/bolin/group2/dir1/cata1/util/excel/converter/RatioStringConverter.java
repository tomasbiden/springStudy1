package com.bolin.group2.dir1.cata1.util.excel.converter;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.math.BigDecimal;

/**
 * 对百分比做特殊处理：读取时自动去除百分号，并且乘0.01；导出时自动乘100并且加上百分号
 */
public class RatioStringConverter implements Converter<BigDecimal> {
    @Override
    public Class<BigDecimal> supportJavaTypeKey() {
        return BigDecimal.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 这里读的时候会调用
     * 将百分比转化为纯数字文本
     *
     * @param cellData            NotNull
     * @param contentProperty     Nullable
     * @param globalConfiguration NotNull
     * @return
     */
    @Override
    public BigDecimal convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String value = cellData.getStringValue();
        if (StrUtil.isBlank(value)) {
            return cellData.getNumberValue();
        }
        if (value.endsWith("%")) {
            value = StrUtil.removeSuffix(value, "%");
            return NumberUtil.mul(new BigDecimal(value), BigDecimal.valueOf(0.01));
        }
        return new BigDecimal(value);
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
    public WriteCellData<?> convertToExcelData(BigDecimal value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<>(NumberUtil.mul(value, BigDecimal.valueOf(100)).stripTrailingZeros().toPlainString() + "%");
    }
}