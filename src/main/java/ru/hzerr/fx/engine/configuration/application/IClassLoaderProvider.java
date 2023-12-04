package ru.hzerr.fx.engine.configuration.application;

import org.jetbrains.annotations.NotNull;

public interface IClassLoaderProvider {

    @NotNull ClassLoader getApplicationClassLoader();
    @NotNull ClassLoader getSpringContextClassLoader();
    @NotNull ClassLoader getApplicationResourceClassLoader();

    void setApplicationClassLoader(@NotNull ClassLoader applicationClassLoader);
    void setSpringContextClassLoader(@NotNull ClassLoader springContextClassLoader);
    void setApplicationResourceClassLoader(@NotNull ClassLoader applicationResourceClassLoader);
}
