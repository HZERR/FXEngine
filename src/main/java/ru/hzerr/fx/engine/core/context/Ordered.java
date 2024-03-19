package ru.hzerr.fx.engine.core.context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface Ordered {

    int value() default LOWEST_PRECEDENCE;

    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;
    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;
}
