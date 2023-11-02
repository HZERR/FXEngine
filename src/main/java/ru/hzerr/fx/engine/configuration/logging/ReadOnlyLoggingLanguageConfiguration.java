package ru.hzerr.fx.engine.configuration.logging;

import ru.hzerr.fx.engine.configuration.interfaces.hardcode.IReadOnlyLoggingLanguageConfiguration;
import ru.hzerr.fx.engine.core.language.LanguagePack;

public class ReadOnlyLoggingLanguageConfiguration implements IReadOnlyLoggingLanguageConfiguration {

    private LanguagePack engineLanguagePack;
    private LanguagePack applicationLanguagePack;

    public ReadOnlyLoggingLanguageConfiguration(LanguagePack engineLanguagePack, LanguagePack applicationLanguagePack) {
        this.engineLanguagePack = engineLanguagePack;
        this.applicationLanguagePack = applicationLanguagePack;
    }

    @Override
    public LanguagePack getEngineLanguagePack() {
        return engineLanguagePack;
    }

    @Override
    public LanguagePack getApplicationLanguagePack() {
        return applicationLanguagePack;
    }
}
