package ru.hzerr.fx.engine.configuration.interfaces;

import ru.hzerr.fx.engine.configuration.interfaces.hardcode.IReadOnlyLoggingLanguageConfiguration;
import ru.hzerr.fx.engine.core.language.LanguagePack;

public interface ILoggingLanguageConfiguration extends InMemoryConfiguration<IReadOnlyLoggingLanguageConfiguration> {

    void setEngineLanguagePack(LanguagePack pack);
    void setApplicationLanguagePack(LanguagePack pack);
}
