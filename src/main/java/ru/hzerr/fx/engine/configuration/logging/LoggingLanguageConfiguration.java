package ru.hzerr.fx.engine.configuration.logging;

import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.configuration.interfaces.ILoggingLanguageConfiguration;
import ru.hzerr.fx.engine.configuration.interfaces.hardcode.IReadOnlyLoggingLanguageConfiguration;
import ru.hzerr.fx.engine.core.language.LanguagePack;

@Registered
public class LoggingLanguageConfiguration implements ILoggingLanguageConfiguration {

    private LanguagePack engineLanguagePack;
    private LanguagePack applicationLanguagePack;

    @Override
    public void setEngineLanguagePack(LanguagePack engineLanguagePack) {
        this.engineLanguagePack = engineLanguagePack;
    }

    @Override
    public void setApplicationLanguagePack(LanguagePack applicationLanguagePack) {
        this.applicationLanguagePack = applicationLanguagePack;
    }

    @Override
    public IReadOnlyLoggingLanguageConfiguration getReadOnlyConfiguration() {
        return new ReadOnlyLoggingLanguageConfiguration(engineLanguagePack, applicationLanguagePack);
    }
}
