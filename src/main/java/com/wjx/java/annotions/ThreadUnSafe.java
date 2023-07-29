package com.wjx.java.annotions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author wangjiaxing
 * @Date 2023/2/16
 */
@Target(value = {ElementType.TYPE,ElementType.FIELD,ElementType.METHOD,ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.SOURCE)
public @interface ThreadUnSafe {
}
