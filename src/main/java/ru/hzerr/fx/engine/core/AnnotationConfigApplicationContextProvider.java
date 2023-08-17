package ru.hzerr.fx.engine.core;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class AnnotationConfigApplicationContextProvider implements IApplicationContextProvider<ExtendedAnnotationConfigApplicationContext> {
    
    private static final String FRAMEWORK_PKG = "ru.hzerr.fx.engine";

    private final String[] basePackages;

    public AnnotationConfigApplicationContextProvider(String[] basePackages, boolean useFrameworkClassPath) {
        if (useFrameworkClassPath) {
            basePackages = Arrays.copyOf(basePackages, basePackages.length + 1);
            basePackages[basePackages.length - 1] = FRAMEWORK_PKG;
        }

        this.basePackages = basePackages;
    }

    @Override
    public ExtendedAnnotationConfigApplicationContext getApplicationContext() {
        return new ExtendedAnnotationConfigApplicationContext(basePackages);
    }
}
