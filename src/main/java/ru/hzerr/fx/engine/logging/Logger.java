package ru.hzerr.fx.engine.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.AppenderAttachable;
import org.slf4j.spi.LocationAwareLogger;
import org.slf4j.spi.LoggingEventAware;
import ru.hzerr.fx.engine.core.language.Configurable;

import java.io.Serializable;

public class Logger implements org.slf4j.Logger, LocationAwareLogger, LoggingEventAware, AppenderAttachable<ILoggingEvent>, Serializable {

    private org.slf4j.Logger logger;
    private Configurable configuration;
    private boolean internationalizationEnabled;

    public Logger(org.slf4j.Logger logger, Configurable languageConfig, boolean internationalizationEnabled) {
        this.logger = logger;
        this.configuration = languageConfig;
        this.internationalizationEnabled = internationalizationEnabled;
    }

    @Override
    public void debug(String s) {
        if (!internationalizationEnabled) {
            boolean engineCaller = true;
            if (engineCaller) {
                logger.debug(configuration.getConfiguration().getString(s));
            } else
                logger.debug(s);
        } else
            logger.debug(configuration.getConfiguration().getString(s));
    }
}
