package ru.hzerr.fx.engine.configuration.application;

import org.jetbrains.annotations.NotNull;

public interface IReadOnlyClassLoaderProvider {

    @NotNull ClassLoader getApplicationClassLoader();
    @NotNull ClassLoader getSpringContextClassLoader();
    @NotNull ClassLoader getApplicationResourceClassLoader();
}
