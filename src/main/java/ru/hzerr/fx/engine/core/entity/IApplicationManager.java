package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.fx.engine.core.theme.Theme;
import ru.hzerr.fx.engine.core.theme.ThemeMetaData;

import java.util.Locale;

public interface IApplicationManager {
    void register(String id, Controller controller);

    void unregister(String id);

    void register(Theme theme);

    void setLanguage(Locale locale);

    void setTheme(Class<? extends ThemeMetaData> themeMetaDataClass);
    void setTheme(String themeName);

    Theme getTheme();

    Class<? extends ThemeMetaData> getThemeMetaDataClass();
}
