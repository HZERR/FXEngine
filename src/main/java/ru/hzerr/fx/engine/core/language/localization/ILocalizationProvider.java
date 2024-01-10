package ru.hzerr.fx.engine.core.language.localization;

public interface ILocalizationProvider<T extends Localization> {

    T getLocalization();
}
