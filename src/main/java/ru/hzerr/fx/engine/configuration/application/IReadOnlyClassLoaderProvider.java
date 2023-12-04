package ru.hzerr.fx.engine.configuration.application;

import org.jetbrains.annotations.NotNull;
import ru.hzerr.fx.engine.configuration.IReadOnlyConfiguration;

public interface IReadOnlyClassLoaderProvider extends IReadOnlyConfiguration {

    @NotNull ClassLoader getApplicationClassLoader();
    @NotNull ClassLoader getSpringContextClassLoader();
    @NotNull ClassLoader getApplicationResourceClassLoader();
}
