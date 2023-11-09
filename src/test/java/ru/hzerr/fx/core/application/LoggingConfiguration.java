package ru.hzerr.fx.core.application;

import ch.qos.logback.classic.Level;
import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.configuration.logging.ReadOnlyLoggingConfiguration;

import java.util.Locale;

@Registered
public class LoggingConfiguration extends ReadOnlyLoggingConfiguration {

    @Override
    public boolean isInternationalizationEnabled() {
        return true;
    }

    @Override
    public Level getLoggerLevel() {
        return Level.ALL;
    }

    @Override
    public Locale getEngineLocale() {
        return LOCALE_RU;
    }
}
