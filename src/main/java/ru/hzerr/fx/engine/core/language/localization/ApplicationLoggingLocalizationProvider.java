package ru.hzerr.fx.engine.core.language.localization;

import ru.hzerr.fx.engine.core.interfaces.localization.IApplicationLoggingLocalization;
import ru.hzerr.fx.engine.core.interfaces.localization.IApplicationLoggingLocalizationProvider;

public class ApplicationLoggingLocalizationProvider extends LocalizationProvider<IApplicationLoggingLocalization> implements IApplicationLoggingLocalizationProvider {
    public ApplicationLoggingLocalizationProvider(IApplicationLoggingLocalization localization) {
        super(localization);
    }
}
