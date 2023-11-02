package ru.hzerr.fx.engine.application;

import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.configuration.logging.ReadOnlyLoggingConfiguration;

@Registered
public class LoggingConfiguration extends ReadOnlyLoggingConfiguration {

    @Override
    public boolean isInternationalizationEnabled() {
        return true;
    }
}
