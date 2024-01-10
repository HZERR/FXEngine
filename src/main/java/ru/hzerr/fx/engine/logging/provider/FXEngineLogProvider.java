package ru.hzerr.fx.engine.logging.provider;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hzerr.fx.engine.configuration.logging.IReadOnlyLoggingConfiguration;
import ru.hzerr.fx.engine.core.annotation.as.ApplicationLogProvider;
import ru.hzerr.fx.engine.logging.StartupException;

public class FXEngineLogProvider implements ILogProvider {

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
    public void start() throws StartupException {
        applicationLogProvider.start();
        nopLogger.setLevel(Level.OFF);
    }

    @Override
    public IReadOnlyLoggingConfiguration getConfiguration() {
        return applicationLogProvider.getConfiguration();
    }

    @ApplicationLogProvider
    public void setApplicationLogProvider(ILogProvider applicationLogProvider) {
        this.applicationLogProvider = applicationLogProvider;
    }
}
