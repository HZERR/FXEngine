package ru.hzerr.fx.engine.logging.provider;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.interfaces.configuration.IReadOnlyLoggingConfiguration;
import ru.hzerr.fx.engine.core.interfaces.logging.ILogProvider;
import ru.hzerr.fx.engine.logging.StartupException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    private void start() throws StartupException {
        try {
            Method start = applicationLogProvider.getClass().getDeclaredMethod("start");
            start.setAccessible(true);
            start.invoke(applicationLogProvider);
            nopLogger.setLevel(Level.OFF);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new StartupException(e.getMessage(), e);
        }
    }

    @Override
    public IReadOnlyLoggingConfiguration getConfiguration() {
        return applicationLogProvider.getConfiguration();
    }

    @Include
    public void setApplicationLogProvider(ILogProvider applicationLogProvider) {
        this.applicationLogProvider = applicationLogProvider;
    }
}
