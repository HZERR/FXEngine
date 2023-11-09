package ru.hzerr.fx.engine.logging.decorator;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.AppenderAttachable;
import org.slf4j.spi.LocationAwareLogger;
import org.slf4j.spi.LoggingEventAware;

import java.io.Serializable;

public interface ILogger extends org.slf4j.Logger, LocationAwareLogger, LoggingEventAware, AppenderAttachable<ILoggingEvent>, Serializable {
}
