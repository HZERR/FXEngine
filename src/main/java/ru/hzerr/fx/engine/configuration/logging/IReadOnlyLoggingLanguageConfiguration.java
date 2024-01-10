package ru.hzerr.fx.engine.configuration.logging;

import ru.hzerr.fx.engine.configuration.IReadOnlyConfiguration;
import ru.hzerr.fx.engine.core.language.localization.Localization;

public interface IReadOnlyLoggingLanguageConfiguration extends IReadOnlyConfiguration {

    Localization getEngineLocalization();
    Localization getApplicationLocalization();
}
