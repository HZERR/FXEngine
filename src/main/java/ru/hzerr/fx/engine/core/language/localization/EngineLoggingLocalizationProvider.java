package ru.hzerr.fx.engine.core.language.localization;

import ru.hzerr.fx.engine.core.interfaces.localization.IEngineLoggingLocalization;
import ru.hzerr.fx.engine.core.interfaces.localization.IEngineLoggingLocalizationProvider;

public class EngineLoggingLocalizationProvider extends LocalizationProvider<IEngineLoggingLocalization> implements IEngineLoggingLocalizationProvider {
    public EngineLoggingLocalizationProvider(IEngineLoggingLocalization localization) {
        super(localization);
    }
}
