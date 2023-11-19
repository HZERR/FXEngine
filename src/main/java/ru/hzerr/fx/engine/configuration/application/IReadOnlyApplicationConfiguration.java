package ru.hzerr.fx.engine.configuration.application;

import ru.hzerr.fx.engine.configuration.IReadOnlyConfiguration;

import java.util.Locale;

public interface IReadOnlyApplicationConfiguration extends IReadOnlyConfiguration {

    Locale getLocale();
    String getThemeName();
}
