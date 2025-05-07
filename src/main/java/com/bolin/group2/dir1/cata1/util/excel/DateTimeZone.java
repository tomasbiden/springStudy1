package com.bolin.group2.dir1.cata1.util.excel;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DateTimeZone {

    /**
     * Specific value reference {@link java.util.TimeZone#getAvailableIDs()}
     */
    String value() default "";
}