package ru.hzerr.fx.engine.configuration.logging;

import ru.hzerr.fx.engine.core.language.Localization;

public class ReadOnlyLoggingLanguageConfiguration implements IReadOnlyLoggingLanguageConfiguration {

    private Localization engineLanguagePack;
    private Localization applicationLanguagePack;

    public ReadOnlyLoggingLanguageConfiguration(Localization engineLanguagePack, Localization applicationLanguagePack) {
        this.engineLanguagePack = engineLanguagePack;
        this.applicationLanguagePack = applicationLanguagePack;
    }

    @Override
    public Localization getEngineLanguagePack() {
        return engineLanguagePack;
    }

    @Override
    public Localization getApplicationLanguagePack() {
        return applicationLanguagePack;
    }
}
