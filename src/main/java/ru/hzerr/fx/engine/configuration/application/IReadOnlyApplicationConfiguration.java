package ru.hzerr.fx.engine.configuration.application;

import org.jetbrains.annotations.NotNull;
import ru.hzerr.fx.engine.configuration.IReadOnlyConfiguration;

import java.util.Locale;

public interface IReadOnlyApplicationConfiguration extends IReadOnlyConfiguration {

    @NotNull
    Locale getLocale();

    @NotNull
    String getThemeName();

    Locale LOCALE_RU = Locale.of("ru", "RU");
}
