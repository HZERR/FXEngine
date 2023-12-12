package ru.hzerr.fx.engine.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@IncludeAs("applicationLogProvider")
public @interface ApplicationLogProvider {
}
