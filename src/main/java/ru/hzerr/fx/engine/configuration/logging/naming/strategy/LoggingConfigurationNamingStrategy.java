package ru.hzerr.fx.engine.configuration.logging.naming.strategy;

import ru.hzerr.fx.engine.configuration.interfaces.ILoggingConfigurationNamingStrategy;

public class LoggingConfigurationNamingStrategy implements ILoggingConfigurationNamingStrategy {

    @Override
    public String fileName() {
        return "logging.short.file.name";
    }

    @Override
    public String consolePattern() {
        return "logging.console.pattern";
    }

    @Override
    public String filePattern() {
        return "logging.file.pattern";
    }

    @Override
    public String loggerName() {
        return "logging.name";
    }

    @Override
    public String fileEncoding() {
        return "logging.file.encoding";
    }

    @Override
    public String consoleEncoding() {
        return "logging.console.encoding";
    }

    @Override
    public String loggerLevel() {
        return "logging.logger.level";
    }

    @Override
    public String loggingEnabled() {
        return "logging.enabled";
    }

    @Override
    public String engineLoggingEnabled() {
        return "logging.engine.enabled";
    }

    @Override
    public String consoleLoggingEnabled() {
        return "logging.console.enabled";
    }

    @Override
    public String fileLoggingEnabled() {
        return "logging.file.enabled";
    }

    @Override
    public String consolePatternLayout() {
        return "logging.console.pattern.layout";
    }

    @Override
    public String internationalizationEnabled() {
        return "logging.internationalization.enabled";
    }

    @Override
    public String applicationLoggingInternationalizationFileName() {
        return "logging.internationalization.file.name";
    }

    @Override
    public String applicationLocale() {
        return "logging.application.locale";
    }

    @Override
    public String engineLocale() {
        return "logging.engine.locale";
    }
}
