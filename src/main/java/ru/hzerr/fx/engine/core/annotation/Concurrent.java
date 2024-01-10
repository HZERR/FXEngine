package ru.hzerr.fx.engine.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to indicate that a class should be treated as a concurrent object,
 * which means that it can be accessed and modified by multiple threads concurrently.
 * <p>
 * The exact behavior of a concurrent object is determined by the implementation,
 * but it is generally expected that concurrent objects will provide thread-safe access to their data.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Concurrent {
}
