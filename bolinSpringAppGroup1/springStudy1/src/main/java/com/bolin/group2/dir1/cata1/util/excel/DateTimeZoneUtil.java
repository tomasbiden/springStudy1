package com.bolin.group2.dir1.cata1.util.excel;

import com.alibaba.excel.util.StringUtils;

import java.lang.reflect.Field;

public class DateTimeZoneUtil {

    public static String getTimeZone(Field field, String defaultTimeZoneId) {
        DateTimeZone dateTimeZone = field.getAnnotation(DateTimeZone.class);
        if (dateTimeZone == null) {
            // 若是Field没有DateTimeZone注解，则使用全局的
            return defaultTimeZoneId;
        }
        String timeZoneId = dateTimeZone.value();
        if (StringUtils.isEmpty(timeZoneId)) {
            // 若是Field的DateTimeZone注解的值为空，则使用全局的
            return defaultTimeZoneId;
        }
        return timeZoneId;
    }
}