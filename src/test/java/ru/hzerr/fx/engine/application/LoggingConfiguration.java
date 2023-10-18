package ru.hzerr.fx.engine.application;

import ru.hzerr.fx.engine.annotation.LogConfiguration;
import ru.hzerr.fx.engine.application.language.logging.LoggingLanguagePackMetaDataEn;
import ru.hzerr.fx.engine.configuration.BaseLoggingConfiguration;

@LogConfiguration
public class LoggingConfiguration extends BaseLoggingConfiguration {

    public LoggingConfiguration() {
        setInternationalizationEnabled(true);
        setEngineLoggingEnabled(true);
        setApplicationLoggingLanguageMetaData(new LoggingLanguagePackMetaDataEn());
        setApplicationLoggingLanguageFileName("main.json");
    }
}
