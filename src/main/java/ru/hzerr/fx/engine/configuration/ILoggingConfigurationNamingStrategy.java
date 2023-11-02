package ru.hzerr.fx.engine.configuration;

public interface ILoggingConfigurationNamingStrategy {

    String fileName();
    String consolePattern();
    String filePattern();
    String loggerName();
    String fileEncoding();
    String consoleEncoding();
    String loggerLevel();
    String loggingEnabled();
    String engineLoggingEnabled();
    String consoleLoggingEnabled();
    String fileLoggingEnabled();
    String consolePatternLayout();
    String internationalizationEnabled();
}
