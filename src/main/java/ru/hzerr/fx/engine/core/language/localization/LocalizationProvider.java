package ru.hzerr.fx.engine.core.language.localization;

import ru.hzerr.fx.engine.core.language.ILocalization;
import ru.hzerr.fx.engine.core.language.Localization;

public class LocalizationProvider implements ILocalizationProvider {

    private Localization localization;

    public LocalizationProvider(Localization localization) {
        this.localization = localization;
    }

    @Override
    public ILocalization getLocalization() {
        return localization;
    }
}
