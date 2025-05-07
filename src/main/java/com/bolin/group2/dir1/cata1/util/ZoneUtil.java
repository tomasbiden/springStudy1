package com.bolin.group2.dir1.cata1.util;

import cn.hutool.core.util.StrUtil;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * @author jack.wen
 * @since 2022/10/11 11:12
 */
public class ZoneUtil {

    public static final String CN = "Asia/Shanghai";

    public static final TimeZone CN_TZ = TimeZone.getTimeZone(cnZoneId());
    /**
     * 零时区
     */
    public static final String Z = "Z";

    public static final TimeZone Z_TZ = TimeZone.getTimeZone(zeroZoneId());

    public static ZoneId cnZoneId() {
        return ZoneId.of(CN);
    }

    public static ZoneId zeroZoneId() {
        return ZoneId.of(Z);
    }

    /**
     * 获取指定时区和系统时区相差的毫秒数
     */
    public static int getRawOffset(String timeZoneId) {
        if (StrUtil.isBlank(timeZoneId)) {
            return 0;
        }
        return TimeZone.getTimeZone(timeZoneId).getRawOffset();
    }

}
