package ru.hzerr.fx.engine.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated element is a preview feature and may change or be removed in a future release.
 * <p>
 * This annotation is intended to be used by tools that process Java code, such as documentation generators,
 * IDEs, and lint tools, to provide appropriate warnings and other feedback to developers who use preview
 * features in their code.
 * <p>
 * The retention policy of this annotation is {@link RetentionPolicy#SOURCE}, which means that it will be
 * retained only in the source code and will be removed by the compiler. This is intended to ensure that
 * the presence of this annotation does not affect the behavior of the code in any way.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Preview {
}