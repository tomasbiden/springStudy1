package com.bolin.group2.dir1.cata1.util.excel.converter;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.converters.date.DateNumberConverter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.bolin.group2.dir1.cata1.util.ZoneUtil;
import com.bolin.group2.dir1.cata1.util.excel.DateTimeZoneUtil;

import java.util.Date;

public class DateTimeZoneNumberConverter extends DateNumberConverter {

    private final String globalTimeZoneId;

    public DateTimeZoneNumberConverter() {
        super();
        globalTimeZoneId = null;
    }

    public DateTimeZoneNumberConverter(String timeZoneId) {
        super();
        globalTimeZoneId = timeZoneId;
    }

    @Override
    public Date convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                  GlobalConfiguration globalConfiguration) {

        String timeZoneId = getTimeZoneId(contentProperty);

        Date date = super.convertToJavaData(cellData, contentProperty, globalConfiguration);

        return DateUtil.offsetMillisecond(date, ZoneUtil.getRawOffset(timeZoneId));
    }

    @Override
    public WriteCellData<?> convertToExcelData(Date value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {

        String timeZoneId = getTimeZoneId(contentProperty);

        DateTime dateTime = DateUtil.offsetMillisecond(value, ZoneUtil.getRawOffset(timeZoneId));

        return super.convertToExcelData(dateTime, contentProperty, globalConfiguration);
    }

    private String getTimeZoneId(ExcelContentProperty contentProperty) {
        if (contentProperty == null) {
            return null;
        }
        return DateTimeZoneUtil.getTimeZone(contentProperty.getField(), globalTimeZoneId);
    }
}