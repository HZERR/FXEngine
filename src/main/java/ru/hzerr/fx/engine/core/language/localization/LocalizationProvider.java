package ru.hzerr.fx.engine.core.language.localization;

import ru.hzerr.fx.engine.core.interfaces.localization.ILocalization;
import ru.hzerr.fx.engine.core.interfaces.localization.ILocalizationProvider;

public abstract class LocalizationProvider<T extends ILocalization> implements ILocalizationProvider<T> {

    private final T localization;

    protected LocalizationProvider(T localization) {
        this.localization = localization;
    }

    @Override
    public T getLocalization() {
        return localization;
    }
}
