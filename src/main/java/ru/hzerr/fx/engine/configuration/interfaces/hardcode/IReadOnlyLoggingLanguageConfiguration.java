package ru.hzerr.fx.engine.configuration.interfaces.hardcode;

import ru.hzerr.fx.engine.configuration.interfaces.IReadOnlyConfiguration;
import ru.hzerr.fx.engine.core.language.LanguagePack;

public interface IReadOnlyLoggingLanguageConfiguration extends IReadOnlyConfiguration {

    LanguagePack getEngineLanguagePack();
    LanguagePack getApplicationLanguagePack();
}
