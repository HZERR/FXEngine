package ru.hzerr.fx.engine.configuration.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.PatternLayout;

import java.nio.charset.Charset;
import java.util.Locale;

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
    /**
     * Пример: internationalization.json
     * @return имя файла, в котором хранится информация об отладке приложения на конкретном языке
     */
    String getApplicationLoggingLanguageFileName();
    Locale getApplicationLocale();
    Locale getEngineLocale();
}
