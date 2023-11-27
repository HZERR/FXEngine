package ru.hzerr.fx.engine.core;

import ru.hzerr.collections.list.HList;
import ru.hzerr.collections.map.HMap;
import ru.hzerr.collections.map.Type;
import ru.hzerr.fx.engine.configuration.application.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.IncludeAs;
import ru.hzerr.fx.engine.core.annotation.Multithreaded;
import ru.hzerr.fx.engine.core.annotation.Redefinition;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.entity.IApplicationManager;
import ru.hzerr.fx.engine.core.entity.exception.LanguagePackMetaDataNotFoundException;
import ru.hzerr.fx.engine.core.language.ApplicationLocalizationMetaData;
import ru.hzerr.fx.engine.core.language.BaseLocalizationMetaData;
import ru.hzerr.fx.engine.core.language.Localization;
import ru.hzerr.fx.engine.core.language.LocalizationLoader;
import ru.hzerr.fx.engine.core.language.localization.ILocalizationProvider;
import ru.hzerr.fx.engine.core.path.*;
import ru.hzerr.fx.engine.core.theme.*;

import java.util.Locale;

@Multithreaded
@Redefinition(isRedefined = false)
public class ApplicationManager implements IApplicationManager {

    private final HMap<String, Controller> controllers = HMap.create(Type.SYNCHRONIZED);
    private final HList<ResolvedThemeLocation> themes = HList.createProtectedList();
    private final HList<ThemeMetaData> themesMetaData = HList.createProtectedList();

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
    public void register(ThemeMetaData themeMetaData) {
        themesMetaData.add(themeMetaData);
    }

    @Override
    public void setLanguage(Locale locale) {
        controllers.forEach((id, controller) -> controller.onChangeLanguage(getControllerInternationalizationFile(controller, locale)));
        FXEngine.getContext().getApplicationConfiguration().setLocale(locale);
    }

    @Override
    public void changeTheme(Class<? extends ThemeMetaData> themeMetaDataClass) throws ResolveThemeException {
        ThemeMetaData themeMetaData = themesMetaData
                .find(data -> data.getClass().equals(themeMetaDataClass))
                .orElseThrow(() -> new ThemeNotFoundException("Unknown themeMetaDataClass: " + themeMetaDataClass.getSimpleName()));

        controllers.forEach((id, controller) -> controller.changeTheme(resolve(themeMetaData, controller)), ResolveThemeException.class);
        FXEngine.getContext().getApplicationConfiguration().setThemeName(themeMetaData.getName());
    }

    @Override
    public void changeTheme(String themeName) throws ResolveThemeException {
        ThemeMetaData themeMetaData = themesMetaData
                .find(data -> data.getName().equals(themeName))
                .orElseThrow(() -> new ThemeNotFoundException("Unknown theme: " + themeName));

        controllers.forEach((id, controller) -> controller.changeTheme(resolve(themeMetaData, controller)), ResolveThemeException.class);
        FXEngine.getContext().getApplicationConfiguration().setThemeName(themeMetaData.getName());
    }

    @Override
    public ThemeMetaData getThemeMetaData() {
        String name = FXEngine.getContext().getApplicationConfiguration().getThemeName();
        return themesMetaData.find(themeMetaData -> themeMetaData.getName().equals(name))
                .orElseThrow(() -> new ThemeNotFoundException("Theme '" + name + "' can't be found"));
    }

    @Override
    public <C extends Controller> ResolvedThemeLocation resolve(ThemeMetaData themeMetaData, C controller) throws ResolveThemeException {
        CSSResolver loader = FXEngine.getContext().getBean(CSSResolver.class, themeMetaData, controller);
        loader.setInitialLocation(FXEngine.getContext().getResourceStructureConfiguration().getThemePackage());
        return loader.resolve();
    }

    @Override
    public <C extends Controller> ResolvedThemeLocation resolve(C controller) throws ResolveThemeException {
        return resolve(getThemeMetaData(), controller);
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
        return localizationLoader.resolve();
    }

    private BaseLocalizationMetaData getApplicationLanguageMetaData(Locale locale) {
        for (BaseLocalizationMetaData metaData : FXEngine.getContext().getBeansOfType(ApplicationLocalizationMetaData.class).values()) {
            if (metaData.getLocale().equals(locale)) {
                return metaData;
            }
        }

        throw new LanguagePackMetaDataNotFoundException(localizationProvider.getLocalization().getConfiguration().getString(
                "fx.engine.controller.getApplicationLanguageMetaData.languageMetaDataNotFoundException", locale.getLanguage() + "/" + locale.getCountry()
        ));
    }
}
