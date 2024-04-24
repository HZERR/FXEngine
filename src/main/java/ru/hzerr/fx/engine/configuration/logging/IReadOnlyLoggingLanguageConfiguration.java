package ru.hzerr.fx.engine.configuration.logging;

import ru.hzerr.fx.engine.core.language.localization.Localization;

public interface IReadOnlyLoggingLanguageConfiguration {

    Localization getEngineLocalization();
    Localization getApplicationLocalization();
}
