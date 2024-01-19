package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.fx.engine.core.theme.LoadThemeException;
import ru.hzerr.fx.engine.core.theme.LoadedThemeData;
import ru.hzerr.fx.engine.core.theme.ThemeMetaData;

import java.util.Locale;

public interface IApplicationManager {
    void register(String id, Controller controller);

    void unregister(String id);

    void register(ThemeMetaData themeMetaData);

    void setLanguage(Locale locale);

    void changeTheme(Class<? extends ThemeMetaData> themeMetaDataClass) throws LoadThemeException;
    void changeTheme(String themeName) throws LoadThemeException;

    <C extends Controller> void applyTheme(C controller) throws LoadThemeException;


    ThemeMetaData getThemeMetaData();

    <C extends Controller> LoadedThemeData load(ThemeMetaData themeMetaData, C controller) throws LoadThemeException;
    <C extends Controller> LoadedThemeData load(C controller) throws LoadThemeException;
}
