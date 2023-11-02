package ru.hzerr.fx.engine.configuration.hardcode;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.PatternLayout;

import java.nio.charset.Charset;

public interface IReadOnlyLoggingConfiguration {

    String getLogFileName();
    String getLoggerConsolePattern();
    String getLoggerFilePattern();
    String getLoggerName();
    Charset getFileEncoding();
    Charset getConsoleEncoding();
    PatternLayout getConsolePatternLayout();
    Level getLoggerLevel();
    boolean isEngineLoggingEnabled();
    boolean isEnabled();
    boolean isConsoleLoggingEnabled();
    boolean isFileLoggingEnabled();
    boolean isInternationalizationEnabled();
}
