package ru.hzerr.fx.core.application;

import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.configuration.application.IReadOnlyApplicationConfiguration;

import java.util.Locale;

@Registered
public class ReadOnlyApplicationConfiguration implements IReadOnlyApplicationConfiguration {

    @Override
    public Locale getLocale() {
        return Locale.ENGLISH;
    }
}
