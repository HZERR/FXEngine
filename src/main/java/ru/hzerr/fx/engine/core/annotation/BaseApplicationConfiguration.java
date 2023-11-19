package ru.hzerr.fx.engine.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RegisteredAs("baseApplicationConfiguration")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseApplicationConfiguration {
}
