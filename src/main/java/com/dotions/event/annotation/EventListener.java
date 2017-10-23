package com.dotions.event.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

@Component
@Documented
@Retention(RUNTIME)
@Target(TYPE)
/**
 * Created by Dotions on 2017-10-20.
 */
public @interface EventListener {

    /** 监听器的顺序 */
    int order() default 0;

    /** 被监听事件的类型 */
    @AliasFor("eventType")
    int value() default 0;

    /** 被监听事件的类型 */
    @AliasFor("value")
    int eventType() default 0;
}
