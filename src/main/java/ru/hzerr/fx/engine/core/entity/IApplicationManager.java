package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.fx.engine.core.annotation.Preview;
import ru.hzerr.fx.engine.core.language.Localization;
import ru.hzerr.fx.engine.core.theme.ResolveThemeException;
import ru.hzerr.fx.engine.core.theme.ResolvedThemeLocation;
import ru.hzerr.fx.engine.core.theme.ThemeMetaData;

import java.util.Locale;

public interface IApplicationManager {
    void register(String id, Controller controller);

    void unregister(String id);

    void register(ThemeMetaData themeMetaData);

    void setLanguage(Locale locale);

    void changeTheme(Class<? extends ThemeMetaData> themeMetaDataClass) throws ResolveThemeException;
    void changeTheme(String themeName) throws ResolveThemeException;

    @Preview
    Localization getLocalization(Controller controller, Locale locale);

    <C extends Controller> void applyTheme(C controller) throws ResolveThemeException;


    ThemeMetaData getThemeMetaData();

    <C extends Controller> ResolvedThemeLocation resolve(ThemeMetaData themeMetaData, C controller) throws ResolveThemeException;
    <C extends Controller> ResolvedThemeLocation resolve(C controller) throws ResolveThemeException;
}
