package ru.hzerr.fx.engine.annotation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Qualifier
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogProvider {
    String value() default "";
}
