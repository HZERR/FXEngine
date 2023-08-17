package ru.hzerr.fx.engine.logging.factory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.hzerr.fx.engine.annotation.LogProvider;
import ru.hzerr.fx.engine.configuration.ILoggingConfiguration;
import ru.hzerr.fx.engine.logging.ConfigurableException;

@LogProvider("engine.log.provider")
public class FXEngineLogProvider implements ILogProvider {

    @Autowired
    @Qualifier("application.log.provider")
    private ILogProvider applicationLogProvider;

    private Logger nopLogger = ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("NOPLogger");

    @Override
    public Logger getLogger() {
        if (applicationLogProvider.getConfiguration().isFrameworkLoggingEnabled()) {
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
    public ILoggingConfiguration getConfiguration() {
        return applicationLogProvider.getConfiguration();
    }
}
