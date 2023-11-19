package ru.hzerr.fx.core.application;

import ch.qos.logback.classic.Level;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.configuration.logging.ReadOnlyLoggingConfiguration;

import java.util.Locale;

@Registered
public class BaseReadOnlyLoggingConfiguration extends ReadOnlyLoggingConfiguration {

    @Override
    public boolean isInternationalizationEnabled() {
        return false;
    }

    @Override
    public Level getLoggerLevel() {
        return Level.DEBUG;
    }

    @Override
    public Locale getEngineLocale() {
        return LOCALE_RU;
    }
}
