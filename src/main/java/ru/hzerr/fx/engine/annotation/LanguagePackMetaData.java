package ru.hzerr.fx.engine.annotation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Component
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface LanguagePackMetaData {
    String value() default "";
}
