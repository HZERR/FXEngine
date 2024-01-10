package ru.hzerr.fx.engine.configuration.logging;

import ru.hzerr.fx.engine.configuration.InMemoryReadOnlyConfiguration;
import ru.hzerr.fx.engine.core.language.localization.Localization;

public interface ILoggingLanguageConfiguration extends InMemoryReadOnlyConfiguration<IReadOnlyLoggingLanguageConfiguration> {

    void setEngineLocalization(Localization localization);
    void setApplicationLocalization(Localization localization);
}
