package com.jt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义自定义注解，Aop实现
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheFind {
    // 1、key可以动态获取，类名.方法名::第一个参数
    // 2、key也可以自己指定
    String key() default ""; // 可以给指定的key值

    int second() default 0; // 给定存放时长
}
