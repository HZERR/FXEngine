package ru.hzerr.fx.engine.configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.PatternLayout;
import ru.hzerr.fx.engine.core.language.BaseLanguagePackMetaData;

import java.nio.charset.Charset;

public interface ILoggingConfiguration {

    String getNextLogFileName();

    void setNextLogFileName(String logFileName);

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


    BaseLanguagePackMetaData getApplicationLoggingLanguageMetaData();
    BaseLanguagePackMetaData getEngineLoggingLanguageMetaData();

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

    void setEnabledConsole(boolean consoleEnabled);

    boolean isFileLoggingEnabled();

    void setEnabledFileLogging(boolean fileLoggingEnabled);

    boolean isInternationalizationEnabled();
    void setInternationalizationEnabled(boolean internationalizationEnabled);
}
