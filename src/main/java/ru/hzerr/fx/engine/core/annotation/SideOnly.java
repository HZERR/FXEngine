package ru.hzerr.fx.engine.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Указывает, что аннотированный элемент должен вызываться только на определенной стороне
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface SideOnly {

    /**
     * Сторона, на которой должен вызывается аннотированный элемент.
     */
    Side value();
}
