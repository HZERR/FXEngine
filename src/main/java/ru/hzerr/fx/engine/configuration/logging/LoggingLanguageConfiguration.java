package ru.hzerr.fx.engine.configuration.logging;

import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.core.language.Localization;

@Registered
public class LoggingLanguageConfiguration implements ILoggingLanguageConfiguration {

    private Localization engineLanguagePack;
    private Localization applicationLanguagePack;

    @Override
    public void setEngineLanguagePack(Localization engineLanguagePack) {
        this.engineLanguagePack = engineLanguagePack;
    }

    @Override
    public void setApplicationLanguagePack(Localization applicationLanguagePack) {
        this.applicationLanguagePack = applicationLanguagePack;
    }

    @Override
    public IReadOnlyLoggingLanguageConfiguration getReadOnlyConfiguration() {
        return new ReadOnlyLoggingLanguageConfiguration(engineLanguagePack, applicationLanguagePack);
    }
}
