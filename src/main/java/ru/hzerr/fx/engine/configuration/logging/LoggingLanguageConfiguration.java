package ru.hzerr.fx.engine.configuration.logging;

import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.language.localization.Localization;

@Registered
public class LoggingLanguageConfiguration implements ILoggingLanguageConfiguration {

    private Localization engineLocalization;
    private Localization applicationLocalization;

    @Override
    public void setEngineLocalization(Localization engineLocalization) {
        this.engineLocalization = engineLocalization;
    }

    @Override
    public void setApplicationLocalization(Localization applicationLocalization) {
        this.applicationLocalization = applicationLocalization;
    }

    @Override
    public IReadOnlyLoggingLanguageConfiguration getReadOnlyConfiguration() {
        return new ReadOnlyLoggingLanguageConfiguration(engineLocalization, applicationLocalization);
    }
}
