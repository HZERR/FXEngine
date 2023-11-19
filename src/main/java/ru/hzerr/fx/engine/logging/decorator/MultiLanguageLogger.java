package ru.hzerr.fx.engine.logging.decorator;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;
import org.slf4j.spi.LoggingEventBuilder;
import ru.hzerr.fx.engine.configuration.fs.typesafe.IFormattedConfiguration;
import ru.hzerr.fx.engine.core.language.ICombineLocalization;

import java.util.Iterator;

public class MultiLanguageLogger implements ILogger {

    private final Logger logger;
    private final transient IFormattedConfiguration config;

    public MultiLanguageLogger(ch.qos.logback.classic.Logger logger, ICombineLocalization combineLocalization) {
        this.logger = logger;
        this.config = combineLocalization.getConfiguration();
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
    public Appender<ILoggingEvent> getAppender(String s) {
        return logger.getAppender(s);
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
    public boolean detachAppender(String s) {
        return logger.detachAppender(s);
    }

    @Override
    public void log(Marker marker, String s, int i, String s1, Object[] objects, Throwable throwable) {
        logger.log(marker, s, i, config.getString(s), objects, throwable);
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public LoggingEventBuilder makeLoggingEventBuilder(Level level) {
        return logger.makeLoggingEventBuilder(level);
    }

    @Override
    public LoggingEventBuilder atLevel(Level level) {
        return logger.atLevel(level);
    }

    @Override
    public boolean isEnabledForLevel(Level level) {
        return logger.isEnabledForLevel(level);
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public void trace(String s) {
        logger.trace(config.getString(s));
    }

    @Override
    public void trace(String s, Object o) {
        logger.trace(config.getString(s), o);
    }

    @Override
    public void trace(String s, Object o, Object o1) {
        logger.trace(config.getString(s), o, o1);
    }

    @Override
    public void trace(String s, Object... objects) {
        logger.trace(config.getString(s), objects);
    }

    @Override
    public void trace(String s, Throwable throwable) {
        logger.trace(config.getString(s), throwable);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    @Override
    public LoggingEventBuilder atTrace() {
        return logger.atTrace();
    }

    @Override
    public void trace(Marker marker, String s) {
        logger.trace(marker, config.getString(s));
    }

    @Override
    public void trace(Marker marker, String s, Object o) {
        logger.trace(marker, config.getString(s), o);
    }

    @Override
    public void trace(Marker marker, String s, Object o, Object o1) {
        logger.trace(marker, config.getString(s), o, o1);
    }

    @Override
    public void trace(Marker marker, String s, Object... objects) {
        logger.trace(marker, config.getString(s), objects);
    }

    @Override
    public void trace(Marker marker, String s, Throwable throwable) {
        logger.trace(marker, config.getString(s), throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void debug(String s) {
        logger.debug(config.getString(s));
    }

    @Override
    public void debug(String s, Object o) {
        logger.debug(config.getString(s), o);
    }

    @Override
    public void debug(String s, Object o, Object o1) {
        logger.debug(config.getString(s), o, o1);
    }

    @Override
    public void debug(String s, Object... objects) {
        logger.debug(config.getString(s), objects);
    }

    @Override
    public void debug(String s, Throwable throwable) {
        logger.debug(config.getString(s), throwable);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return logger.isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String s) {
        logger.debug(marker, config.getString(s));
    }

    @Override
    public void debug(Marker marker, String s, Object o) {
        logger.debug(marker, config.getString(s), o);
    }

    @Override
    public void debug(Marker marker, String s, Object o, Object o1) {
        logger.debug(marker, config.getString(s), o, o1);
    }

    @Override
    public void debug(Marker marker, String s, Object... objects) {
        logger.debug(marker, config.getString(s), objects);
    }

    @Override
    public void debug(Marker marker, String s, Throwable throwable) {
        logger.debug(marker, config.getString(s), throwable);
    }

    @Override
    public LoggingEventBuilder atDebug() {
        return logger.atDebug();
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void info(String s) {
        logger.info(config.getString(s));
    }

    @Override
    public void info(String s, Object o) {
        logger.info(config.getString(s), o);
    }

    @Override
    public void info(String s, Object o, Object o1) {
        logger.info(config.getString(s), o, o1);
    }

    @Override
    public void info(String s, Object... objects) {
        logger.info(config.getString(s), objects);
    }

    @Override
    public void info(String s, Throwable throwable) {
        logger.info(config.getString(s), throwable);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return logger.isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String s) {
        logger.info(marker, config.getString(s));
    }

    @Override
    public void info(Marker marker, String s, Object o) {
        logger.info(marker, config.getString(s), o);
    }

    @Override
    public void info(Marker marker, String s, Object o, Object o1) {
        logger.info(marker, config.getString(s), o, o1);
    }

    @Override
    public void info(Marker marker, String s, Object... objects) {
        logger.info(marker, config.getString(s), objects);
    }

    @Override
    public void info(Marker marker, String s, Throwable throwable) {
        logger.info(marker, config.getString(s), throwable);
    }

    @Override
    public LoggingEventBuilder atInfo() {
        return logger.atInfo();
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public void warn(String s) {
        logger.warn(config.getString(s));
    }

    @Override
    public void warn(String s, Object o) {
        logger.warn(config.getString(s), o);
    }

    @Override
    public void warn(String s, Object... objects) {
        logger.warn(config.getString(s), objects);
    }

    @Override
    public void warn(String s, Object o, Object o1) {
        logger.warn(config.getString(s), o, o1);
    }

    @Override
    public void warn(String s, Throwable throwable) {
        logger.warn(config.getString(s), throwable);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return logger.isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String s) {
        logger.warn(marker, config.getString(s));
    }

    @Override
    public void warn(Marker marker, String s, Object o) {
        logger.warn(marker, config.getString(s), o);
    }

    @Override
    public void warn(Marker marker, String s, Object o, Object o1) {
        logger.warn(marker, config.getString(s), o, o1);
    }

    @Override
    public void warn(Marker marker, String s, Object... objects) {
        logger.warn(marker, config.getString(s), objects);
    }

    @Override
    public void warn(Marker marker, String s, Throwable throwable) {
        logger.warn(marker, config.getString(s), throwable);
    }

    @Override
    public LoggingEventBuilder atWarn() {
        return logger.atWarn();
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public void error(String s) {
        logger.error(config.getString(s));
    }

    @Override
    public void error(String s, Object o) {
        logger.error(config.getString(s), o);
    }

    @Override
    public void error(String s, Object o, Object o1) {
        logger.error(config.getString(s), o, o1);
    }

    @Override
    public void error(String s, Object... objects) {
        logger.error(config.getString(s), objects);
    }

    @Override
    public void error(String s, Throwable throwable) {
        logger.error(config.getString(s), throwable);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return logger.isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String s) {
        logger.error(marker, config.getString(s));
    }

    @Override
    public void error(Marker marker, String s, Object o) {
        logger.error(marker, config.getString(s), o);
    }

    @Override
    public void error(Marker marker, String s, Object o, Object o1) {
        logger.error(marker, config.getString(s), o, o1);
    }

    @Override
    public void error(Marker marker, String s, Object... objects) {
        logger.error(marker, config.getString(s), objects);
    }

    @Override
    public void error(Marker marker, String s, Throwable throwable) {
        logger.error(marker, config.getString(s), throwable);
    }

    @Override
    public LoggingEventBuilder atError() {
        return logger.atError();
    }

    @Override
    public void log(LoggingEvent loggingEvent) {
        logger.log(loggingEvent);
    }
}
