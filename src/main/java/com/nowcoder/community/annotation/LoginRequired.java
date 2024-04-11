package com.nowcoder.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: yyp
 * @Date: 2024/4/11 - 04 - 11 - 19:42
 * @Description: com.nowcoder.community.annotation
 * @version: 1.0
 */

// 声明该注解可以用在方法上
@Target(ElementType.METHOD)
// 声明注解的有效时长
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {
}
