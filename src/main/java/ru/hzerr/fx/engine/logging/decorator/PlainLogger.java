package ru.hzerr.fx.engine.logging.decorator;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Marker;
import org.slf4j.event.LoggingEvent;
import ru.hzerr.fx.engine.core.interfaces.localization.IFormattedConfiguration;
import ru.hzerr.fx.engine.core.interfaces.localization.ILocalization;

import java.util.Iterator;

public class PlainLogger implements ILogger {

    private Logger logger;
    private IFormattedConfiguration configuration;

    public PlainLogger(Logger logger, ILocalization configuration) {
        this.logger = logger;
        this.configuration = configuration.getConfiguration();
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public void trace(String s) {
        logger.trace(isEngineCaller() ? configuration.getString(s) : s);
    }

    @Override
    public void trace(String s, Object o) {
        logger.trace(isEngineCaller() ? configuration.getString(s) : s, o);
    }

    @Override
    public void trace(String s, Object o, Object o1) {
        logger.trace(isEngineCaller() ? configuration.getString(s) : s, o, o1);
    }

    @Override
    public void trace(String s, Object... objects) {
        logger.trace(isEngineCaller() ? configuration.getString(s) : s, objects);
    }

    @Override
    public void trace(String s, Throwable throwable) {
        logger.trace(isEngineCaller() ? configuration.getString(s) : s, throwable);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String s) {
        logger.trace(marker, isEngineCaller() ? configuration.getString(s) : s);
    }

    @Override
    public void trace(Marker marker, String s, Object o) {
        logger.trace(marker, isEngineCaller() ? configuration.getString(s) : s, o);
    }

    @Override
    public void trace(Marker marker, String s, Object o, Object o1) {
        logger.trace(marker, isEngineCaller() ? configuration.getString(s) : s, o, o1);
    }

    @Override
    public void trace(Marker marker, String s, Object... objects) {
        logger.trace(marker, isEngineCaller() ? configuration.getString(s) : s, objects);
    }

    @Override
    public void trace(Marker marker, String s, Throwable throwable) {
        logger.trace(marker, isEngineCaller() ? configuration.getString(s) : s, throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void debug(String s) {
        logger.debug(isEngineCaller() ? configuration.getString(s) : s);
    }

    @Override
    public void debug(String s, Object o) {
        logger.debug(isEngineCaller() ? configuration.getString(s) : s, o);
    }

    @Override
    public void debug(String s, Object o, Object o1) {
        logger.debug(isEngineCaller() ? configuration.getString(s) : s, o, o1);
    }

    @Override
    public void debug(String s, Object... objects) {
        logger.debug(isEngineCaller() ? configuration.getString(s) : s, objects);

    }

    @Override
    public void debug(String s, Throwable throwable) {
        logger.debug(isEngineCaller() ? configuration.getString(s) : s, throwable);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return logger.isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String s) {
        logger.debug(marker, isEngineCaller() ? configuration.getString(s) : s);
    }

    @Override
    public void debug(Marker marker, String s, Object o) {
        logger.debug(marker, isEngineCaller() ? configuration.getString(s) : s, o);
    }

    @Override
    public void debug(Marker marker, String s, Object o, Object o1) {
        logger.debug(marker, isEngineCaller() ? configuration.getString(s) : s, o, o1);
    }

    @Override
    public void debug(Marker marker, String s, Object... objects) {
        logger.debug(marker, isEngineCaller() ? configuration.getString(s) : s, objects);
    }

    @Override
    public void debug(Marker marker, String s, Throwable throwable) {
        logger.debug(marker, isEngineCaller() ? configuration.getString(s) : s, throwable);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void info(String s) {
        logger.info(isEngineCaller() ? configuration.getString(s) : s);
    }

    @Override
    public void info(String s, Object o) {
        logger.info(isEngineCaller() ? configuration.getString(s) : s, o);
    }

    @Override
    public void info(String s, Object o, Object o1) {
        logger.info(isEngineCaller() ? configuration.getString(s) : s, o, o1);
    }

    @Override
    public void info(String s, Object... objects) {
        logger.info(isEngineCaller() ? configuration.getString(s) : s, objects);
    }

    @Override
    public void info(String s, Throwable throwable) {
        logger.info(isEngineCaller() ? configuration.getString(s) : s, throwable);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return logger.isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String s) {
        logger.info(marker, isEngineCaller() ? configuration.getString(s) : s);
    }

    @Override
    public void info(Marker marker, String s, Object o) {
        logger.info(marker, isEngineCaller() ? configuration.getString(s) : s, o);
    }

    @Override
    public void info(Marker marker, String s, Object o, Object o1) {
        logger.info(marker, isEngineCaller() ? configuration.getString(s) : s, o, o1);
    }

    @Override
    public void info(Marker marker, String s, Object... objects) {
        logger.info(marker, isEngineCaller() ? configuration.getString(s) : s, objects);
    }

    @Override
    public void info(Marker marker, String s, Throwable throwable) {
        logger.info(marker, isEngineCaller() ? configuration.getString(s) : s, throwable);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public void warn(String s) {
        logger.warn(isEngineCaller() ? configuration.getString(s) : s);
    }

    @Override
    public void warn(String s, Object o) {
        logger.warn(isEngineCaller() ? configuration.getString(s) : s, o);
    }

    @Override
    public void warn(String s, Object... objects) {
        logger.warn(isEngineCaller() ? configuration.getString(s) : s, objects);
    }

    @Override
    public void warn(String s, Object o, Object o1) {
        logger.warn(isEngineCaller() ? configuration.getString(s) : s, o, o1);
    }

    @Override
    public void warn(String s, Throwable throwable) {
        logger.warn(isEngineCaller() ? configuration.getString(s) : s, throwable);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return logger.isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String s) {
        logger.warn(marker, isEngineCaller() ? configuration.getString(s) : s);
    }

    @Override
    public void warn(Marker marker, String s, Object o) {
        logger.warn(marker, isEngineCaller() ? configuration.getString(s) : s, o);
    }

    @Override
    public void warn(Marker marker, String s, Object o, Object o1) {
        logger.warn(marker, isEngineCaller() ? configuration.getString(s) : s, o, o1);
    }

    @Override
    public void warn(Marker marker, String s, Object... objects) {
        logger.warn(marker, isEngineCaller() ? configuration.getString(s) : s, objects);
    }

    @Override
    public void warn(Marker marker, String s, Throwable throwable) {
        logger.warn(marker, isEngineCaller() ? configuration.getString(s) : s, throwable);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public void error(String s) {
        logger.error(isEngineCaller() ? configuration.getString(s) : s);
    }

    @Override
    public void error(String s, Object o) {
        logger.error(isEngineCaller() ? configuration.getString(s) : s, o);
    }

    @Override
    public void error(String s, Object o, Object o1) {
        logger.error(isEngineCaller() ? configuration.getString(s) : s, o, o1);
    }

    @Override
    public void error(String s, Object... objects) {
        logger.error(isEngineCaller() ? configuration.getString(s) : s, objects);
    }

    @Override
    public void error(String s, Throwable throwable) {
        logger.error(isEngineCaller() ? configuration.getString(s) : s, throwable);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return logger.isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String s) {
        logger.error(marker, isEngineCaller() ? configuration.getString(s) : s);
    }

    @Override
    public void error(Marker marker, String s, Object o) {
        logger.error(marker, isEngineCaller() ? configuration.getString(s) : s, o);
    }

    @Override
    public void error(Marker marker, String s, Object o, Object o1) {
        logger.error(marker, isEngineCaller() ? configuration.getString(s) : s, o, o1);
    }

    @Override
    public void error(Marker marker, String s, Object... objects) {
        logger.error(marker, isEngineCaller() ? configuration.getString(s) : s, objects);
    }

    @Override
    public void error(Marker marker, String s, Throwable throwable) {
        logger.error(marker, isEngineCaller() ? configuration.getString(s) : s, throwable);
    }

    @Override
    public void addAppender(Appender<ILoggingEvent> appender) {
        logger.addAppender(appender);
    }

    @Override
    public Iterator<Appender<ILoggingEvent>> iteratorForAppenders() {
        return logger.iteratorForAppenders();
    }

    @Override
    public Appender<ILoggingEvent> getAppender(String name) {
        return logger.getAppender(name);
    }

    @Override
    public boolean isAttached(Appender<ILoggingEvent> appender) {
        return logger.isAttached(appender);
    }

    @Override
    public void detachAndStopAllAppenders() {
        logger.detachAndStopAllAppenders();
    }

    @Override
    public boolean detachAppender(Appender<ILoggingEvent> appender) {
        return logger.detachAppender(appender);
    }

    @Override
    public boolean detachAppender(String name) {
        return logger.detachAppender(name);
    }

    @Override
    public void log(Marker marker, String fqcn, int levelInt, String message, Object[] argArray, Throwable throwable) {
        logger.log(marker, fqcn, levelInt, isEngineCaller() ? configuration.getString(message) : message, argArray, throwable);
    }

    @Override
    public void log(LoggingEvent loggingEvent) {
        logger.log(loggingEvent);
    }

    private boolean isEngineCaller() {
        return StringUtils.containsIgnoreCase(Thread.currentThread().getStackTrace()[3].getClassName(), "ru.hzerr.fx.engine");
    }
}
