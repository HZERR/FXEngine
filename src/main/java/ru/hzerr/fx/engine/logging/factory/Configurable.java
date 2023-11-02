package ru.hzerr.fx.engine.logging.factory;

import ru.hzerr.fx.engine.configuration.interfaces.ILoggingConfiguration;
import ru.hzerr.fx.engine.logging.ConfigurableException;

public interface Configurable {

    void configure() throws ConfigurableException;

      ILoggingConfiguration getConfiguration();
}
