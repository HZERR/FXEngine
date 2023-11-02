package ru.hzerr.fx.engine.configuration.interfaces;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.PatternLayout;

import java.nio.charset.Charset;
import java.util.Locale;

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

    void setApplicationLoggingLanguageFileName(String applicationLoggingLanguageFileName);

    /**
     * Пример: internationalization.json
     * @return имя файла, в котором хранится информация об отладке приложения на конкретном языке
     */
    String getApplicationLoggingLanguageFileName();

    boolean isEngineLoggingEnabled();

    void setEngineLoggingEnabled(boolean enabled);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    boolean isConsoleLoggingEnabled();

    void setConsoleLoggingEnabled(boolean consoleLoggingEnabled);

    boolean isFileLoggingEnabled();

    void setFileLoggingEnabled(boolean fileLoggingEnabled);

    boolean isInternationalizationEnabled();
    void setInternationalizationEnabled(boolean internationalizationEnabled);

    Locale getApplicationLocale();
    Locale getEngineLocale();
}
