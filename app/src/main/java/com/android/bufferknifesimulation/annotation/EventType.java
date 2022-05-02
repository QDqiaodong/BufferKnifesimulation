package com.android.bufferknifesimulation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description desc
 * @Author qiaodong
 * @Date 2022/2/24-9:52 上午
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface EventType {
    Class listenerType();

    String listenerSetter();
}
