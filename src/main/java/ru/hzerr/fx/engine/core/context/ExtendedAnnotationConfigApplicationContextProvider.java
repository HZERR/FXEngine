package ru.hzerr.fx.engine.core.context;

import ru.hzerr.fx.engine.core.IApplicationContextProvider;

import java.util.Arrays;

public class ExtendedAnnotationConfigApplicationContextProvider implements IApplicationContextProvider<IExtendedAnnotationConfigApplicationContext> {

    private static final String ENGINE_PKG = "ru.hzerr.fx.engine";

    private final String[] basePackages;

    public ExtendedAnnotationConfigApplicationContextProvider(String[] basePackages, boolean addFrameworkClassPath) {
        if (addFrameworkClassPath) {
            basePackages = Arrays.copyOf(basePackages, basePackages.length + 1);
            basePackages[basePackages.length - 1] = ENGINE_PKG;
        }

        this.basePackages = basePackages;
    }

    @Override
    public IExtendedAnnotationConfigApplicationContext getContext() {
        return new ExtendedAnnotationConfigApplicationContext(basePackages);
    }
}
