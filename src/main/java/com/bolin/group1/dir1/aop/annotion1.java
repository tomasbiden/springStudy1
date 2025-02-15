package com.bolin.group1.dir1.aop;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface annotion1 {

    String value() default "ceshi";
}
