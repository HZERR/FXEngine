package ru.hzerr.fx.engine.annotation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Component
@Qualifier("base.application.configuration")
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseApplicationConfiguration {
}
