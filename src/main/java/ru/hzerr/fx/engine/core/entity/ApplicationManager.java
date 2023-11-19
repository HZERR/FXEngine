package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.collections.list.HList;
import ru.hzerr.collections.list.SynchronizedHList;
import ru.hzerr.fx.engine.configuration.application.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.IncludeAs;
import ru.hzerr.fx.engine.core.annotation.Multithreaded;
import ru.hzerr.fx.engine.core.annotation.Redefinition;
import ru.hzerr.fx.engine.core.entity.exception.LanguagePackMetaDataNotFoundException;
import ru.hzerr.fx.engine.core.language.ApplicationLocalizationMetaData;
import ru.hzerr.fx.engine.core.language.BaseLocalizationMetaData;
import ru.hzerr.fx.engine.core.language.LocalizationLoader;
import ru.hzerr.fx.engine.core.language.Localization;
import ru.hzerr.fx.engine.core.language.localization.ILocalizationProvider;
import ru.hzerr.fx.engine.core.path.*;
import ru.hzerr.fx.engine.core.theme.Theme;
import ru.hzerr.fx.engine.core.theme.ThemeMetaData;
import ru.hzerr.fx.engine.core.theme.ThemeNotFoundException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Multithreaded
@Redefinition(isRedefined = false)
public class ApplicationManager implements IApplicationManager {

    private final Map<String, Controller> controllers = Collections.synchronizedMap(new HashMap<>());
    private final HList<Theme> themes = new SynchronizedHList<>();

    @IncludeAs("engineLoggingLocalizationProvider")
    private ILocalizationProvider localizationProvider;

    @Include
    private IResourceStructureConfiguration resourceStructureConfiguration;

    @Override
    public void register(String id, Controller controller) {
        controllers.put(id, controller);
    }

    @Override
    public void unregister(String id) {
        controllers.remove(id);
    }

    @Override
    public void register(Theme theme) {
        themes.add(theme);
    }

    @Override
    public void setLanguage(Locale locale) {
        controllers.forEach((id, controller) -> controller.onChangeLanguage(getControllerInternationalizationFile(controller, locale)));
        FXEngine.getContext().getApplicationConfiguration().setLocale(locale);
    }

    @Override
    public void setTheme(Class<? extends ThemeMetaData> themeMetaDataClass) {
        Theme theme = getThemeByMetaData(themeMetaDataClass);
        controllers.forEach((id, controller) -> controller.onChangeUI(theme));
        FXEngine.getContext().getApplicationConfiguration().setThemeName(theme.getMetaData().getName());
    }

    @Override
    public void setTheme(String themeName) {
        Theme theme = getThemeByName(themeName);
        controllers.forEach((id, controller) -> controller.onChangeUI(theme));
        FXEngine.getContext().getApplicationConfiguration().setThemeName(theme.getMetaData().getName());
    }

    @Override
    public Theme getTheme() {
        return getThemeByName(FXEngine.getContext().getApplicationConfiguration().getThemeName());
    }

    @Override
    public Class<? extends ThemeMetaData> getThemeMetaDataClass() {
        return getThemeByName(FXEngine.getContext().getApplicationConfiguration().getThemeName()).getMetaData().getClass();
    }

    private Theme getThemeByName(String themeName) {
        return themes
                .find(theme -> themeName.equals(theme.getMetaData().getName()))
                .orElseThrow(() -> new ThemeNotFoundException("Theme '" + themeName + "' can't be found"));
    }

    private Theme getThemeByMetaData(Class<? extends ThemeMetaData> themeMetaDataClass) {
        return themes
                .find(theme -> theme.getMetaData().getClass().isAssignableFrom(themeMetaDataClass))
                .orElseThrow(() -> new ThemeNotFoundException("Theme '" + themeMetaDataClass.getSimpleName() + "' can't be found"));
    }

    private Localization getControllerInternationalizationFile(Controller controller, Locale locale) {
        BaseLocalizationMetaData currentLanguageMetaData = getApplicationLanguageMetaData(locale);
        String currentLanguagePackageLocation = LocationTools.resolve(
                ResolvableLocation.of(
                        resourceStructureConfiguration.getApplicationInternationalizationPackage(),
                        NullSafeResolveLocationOptions.THROW_EXCEPTION
                ),
                ResolvableLocation.of(
                        currentLanguageMetaData.getILocation(),
                        NullSafeResolveLocationOptions.INSERT_EVERYWHERE
                ),
                SeparatorResolveLocationOptions.INSERT_END,
                Separator.SLASH_SEPARATOR
        );

        String currentLanguagePackLocation = LocationTools.resolve(
                ResolvableLocation.of(currentLanguagePackageLocation, NullSafeResolveLocationOptions.THROW_EXCEPTION),
                ResolvableLocation.of(controller.getMetaData().internationalization(), NullSafeResolveLocationOptions.THROW_EXCEPTION),
                SeparatorResolveLocationOptions.DEFAULT,
                Separator.SLASH_SEPARATOR
        );

        LocalizationLoader localizationLoader = LocalizationLoader.from(currentLanguageMetaData, currentLanguagePackLocation);
        return localizationLoader.load();
    }

    private BaseLocalizationMetaData getApplicationLanguageMetaData(Locale locale) {
        for (BaseLocalizationMetaData metaData: FXEngine.getContext().getBeansOfType(ApplicationLocalizationMetaData.class).values()) {
            if (metaData.getLocale().equals(locale)) {
                return metaData;
            }
        }

        throw new LanguagePackMetaDataNotFoundException(localizationProvider.getLocalization().getConfiguration().getString(
                "fx.engine.controller.getApplicationLanguageMetaData.languageMetaDataNotFoundException", locale.getLanguage() + "/" + locale.getCountry()
        ));
    }
}
