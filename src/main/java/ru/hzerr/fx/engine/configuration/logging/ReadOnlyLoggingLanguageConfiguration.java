package ru.hzerr.fx.engine.configuration.logging;

import ru.hzerr.fx.engine.core.language.localization.Localization;

public class ReadOnlyLoggingLanguageConfiguration implements IReadOnlyLoggingLanguageConfiguration {

    private Localization engineLocalization;
    private Localization applicationLocalization;

    public ReadOnlyLoggingLanguageConfiguration(Localization engineLocalization, Localization applicationLocalization) {
        this.engineLocalization = engineLocalization;
        this.applicationLocalization = applicationLocalization;
    }

    @Override
    public Localization getEngineLocalization() {
        return engineLocalization;
    }

    @Override
    public Localization getApplicationLocalization() {
        return applicationLocalization;
    }
}
