package ru.hzerr.fx.engine.configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.PatternLayout;

import java.nio.charset.Charset;

public interface ILoggingConfiguration {

    String getLogFileName();

    void setLogFileName(String logFileName);

    String getLoggerConsolePattern();

    void setLoggerConsolePattern(String consolePattern);

    String getLoggerFilePattern();

    void setLoggerFilePattern(String filePattern);

    String getLoggerName();

    void setLoggerName(String loggerName);

    Charset getFileEncoding();

    void setFileEncoding(Charset fileEncoding);

    Charset getConsoleEncoding();

    void setConsoleEncoding(Charset consoleEncoding);

    PatternLayout getConsolePatternLayout();

    void setConsolePatternLayout(PatternLayout consolePatternLayout);

    Level getLoggerLevel();

    void setLoggerLevel(Level loggerLevel);

    boolean isFrameworkLoggingEnabled();

    void setFrameworkLoggingEnabled(boolean enabled);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    boolean isConsoleLoggingEnabled();

    void setEnabledConsole(boolean consoleEnabled);

    boolean isFileLoggingEnabled();

    void setEnabledFileLogging(boolean fileLoggingEnabled);
}
