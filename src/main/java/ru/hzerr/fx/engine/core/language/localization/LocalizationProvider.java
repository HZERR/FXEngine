package ru.hzerr.fx.engine.core.language.localization;

public abstract class LocalizationProvider<T extends Localization> implements ILocalizationProvider<T> {

    private T localization;

    protected LocalizationProvider(T localization) {
        this.localization = localization;
    }

    @Override
    public T getLocalization() {
        return localization;
    }
}
