package ru.hzerr.fx.core.application;

import org.jetbrains.annotations.NotNull;
import ru.hzerr.fx.engine.configuration.application.IReadOnlyClassLoaderProvider;
import ru.hzerr.fx.engine.core.annotation.Registered;

@Registered
public class ApplicationClassLoaderProvider implements IReadOnlyClassLoaderProvider {

    @NotNull
    @Override
    public ClassLoader getApplicationClassLoader() {
        return ClassLoader.getSystemClassLoader();
    }

    @NotNull
    @Override
    public ClassLoader getSpringContextClassLoader() {
        return ClassLoader.getSystemClassLoader();
    }

    @NotNull
    @Override
    public ClassLoader getApplicationResourceClassLoader() {
        return ClassLoader.getSystemClassLoader();
    }
}
