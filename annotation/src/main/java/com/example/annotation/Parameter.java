package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 传参注解
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)// 注解作用在属性上
public @interface Parameter {

    /**
     * 名称，如不填，则用变量名代替
     * @return
     */
    String name() default "";
}
