package ru.hzerr.fx.engine.logging.provider;

import ru.hzerr.fx.engine.configuration.logging.IReadOnlyLoggingConfiguration;
import ru.hzerr.fx.engine.logging.ConfigurableException;

public interface Configurable {

    void configure() throws ConfigurableException;

      IReadOnlyLoggingConfiguration getConfiguration();
}
