package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.fx.engine.core.theme.Theme;

import java.util.Locale;

public interface IApplicationManager {
    void register(String id, Controller controller);

    void unregister(String id);

    void setLanguage(Locale locale);

    void setTheme(Theme theme);
}
