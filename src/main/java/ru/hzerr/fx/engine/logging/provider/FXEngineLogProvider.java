package ru.hzerr.fx.engine.logging.provider;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hzerr.fx.engine.configuration.logging.IReadOnlyLoggingConfiguration;
import ru.hzerr.fx.engine.core.annotation.IncludeAs;
import ru.hzerr.fx.engine.logging.ConfigurableException;

public class FXEngineLogProvider implements ILogProvider {

    @IncludeAs("applicationLogProvider")
    private ILogProvider applicationLogProvider;
    private final ch.qos.logback.classic.Logger nopLogger = ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("NOPLogger");

    @Override
    public Logger getLogger() {
        if (applicationLogProvider.getConfiguration().isEngineLoggingEnabled()) {
            return applicationLogProvider.getLogger();
        } else
            return nopLogger;
    }

    @Override
    public void close() {
        // ¯\_(ツ)_/¯
    }

    @Override
    public void configure() throws ConfigurableException {
        applicationLogProvider.configure();
        nopLogger.setLevel(Level.OFF);
    }

    @Override
    public IReadOnlyLoggingConfiguration getConfiguration() {
        return applicationLogProvider.getConfiguration();
    }
}
