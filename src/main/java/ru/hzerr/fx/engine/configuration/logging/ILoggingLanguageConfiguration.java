package ru.hzerr.fx.engine.configuration.logging;

import ru.hzerr.fx.engine.configuration.InMemoryReadOnlyConfiguration;
import ru.hzerr.fx.engine.core.language.Localization;

public interface ILoggingLanguageConfiguration extends InMemoryReadOnlyConfiguration<IReadOnlyLoggingLanguageConfiguration> {

    void setEngineLocalization(Localization pack);
    void setApplicationLocalization(Localization pack);
}
