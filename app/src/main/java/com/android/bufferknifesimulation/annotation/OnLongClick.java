package com.android.bufferknifesimulation.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description desc
 * @Author qiaodong
 * @Date 2022/2/24-9:26 上午
 */
@EventType(listenerSetter = "setOnLongClickListener",listenerType = View.OnLongClickListener.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OnLongClick {
    int[] value();
}
