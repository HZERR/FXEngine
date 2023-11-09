package ru.hzerr.fx.engine.logging.factory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hzerr.fx.engine.annotation.IncludeAs;
import ru.hzerr.fx.engine.annotation.RegisteredAs;
import ru.hzerr.fx.engine.configuration.interfaces.hardcode.IReadOnlyLoggingConfiguration;
import ru.hzerr.fx.engine.logging.ConfigurableException;

@RegisteredAs("engineLogProvider")
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
        throw new UnsupportedOperationException();
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
