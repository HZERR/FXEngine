package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.collections.list.HList;
import ru.hzerr.collections.map.HMap;
import ru.hzerr.collections.map.Type;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.IncludeAs;
import ru.hzerr.fx.engine.core.annotation.Multithreaded;
import ru.hzerr.fx.engine.core.annotation.Preview;
import ru.hzerr.fx.engine.core.annotation.Redefinition;
import ru.hzerr.fx.engine.core.entity.exception.LanguagePackMetaDataNotFoundException;
import ru.hzerr.fx.engine.core.language.ApplicationLocalizationMetaData;
import ru.hzerr.fx.engine.core.language.BaseLocalizationMetaData;
import ru.hzerr.fx.engine.core.language.Localization;
import ru.hzerr.fx.engine.core.language.LocalizationLoader;
import ru.hzerr.fx.engine.core.language.localization.ILocalizationProvider;
import ru.hzerr.fx.engine.core.path.resolver.ControllerLocalizationResolver;
import ru.hzerr.fx.engine.core.theme.*;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

import java.util.Locale;
import java.util.Optional;

@Multithreaded
@Redefinition(isRedefined = false)
public class ApplicationManager implements IApplicationManager {

    private final HMap<String, Controller> controllers = HMap.create(Type.SYNCHRONIZED);
    private final HList<ThemeLoadedData> themeLoadedData = HList.createProtectedList();
    private final HList<ThemeMetaData> themesMetaData = HList.createProtectedList();

    @IncludeAs("engineLoggingLocalizationProvider")
    private ILocalizationProvider localizationProvider;

    @IncludeAs("engineLogProvider")
    private ILogProvider engineLogProvider;

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

    /**
     * Устанавливает язык приложения. Это обновит язык для всех зарегистрированных контроллеров и сохранит настройки в конфигурации.
     *
     * @param locale локаль, которую нужно установить в качестве языка приложения
     */
    @Override
    public void setLanguage(Locale locale) {
        controllers.forEach((id, controller) -> {
            Localization localization = getLocalization(controller, locale);
            controller.onChangeLanguage(localization);
            controller.setLocalization(localization);
        });

        FXEngine.getContext().getApplicationConfiguration().setLocale(locale);
    }

    @Override
    public void changeTheme(Class<? extends ThemeMetaData> themeMetaDataClass) throws ResolveThemeException {
        ThemeMetaData themeMetaData = themesMetaData
                .find(data -> data.getClass().equals(themeMetaDataClass))
                .orElseThrow(() -> new ThemeNotFoundException("Unknown themeMetaDataClass: " + themeMetaDataClass.getSimpleName()));

        controllers.forEach((id, controller) -> {
            Optional<ThemeLoadedData> targetLoadedData = themeLoadedData.find(data -> data.isAllow(controller.getClass(), themeMetaData.getName()));
            ResolvedThemeLocation resolvedThemeLocation;
            if (targetLoadedData.isPresent()) {
                engineLogProvider.getLogger().debug("fxEngine.applicationManager.theme.gettingThemeFromCache", themeMetaData.getName(), controller.getClass().getSimpleName());
                resolvedThemeLocation = targetLoadedData.get().getResolvedThemeLocation();
            } else {
                resolvedThemeLocation = resolve(themeMetaData, controller);
                themeLoadedData.add(new ThemeLoadedData(controller.getClass(), themeMetaData.getName(), resolvedThemeLocation));
            }

            controller.applyTheme(resolvedThemeLocation);
        }, ResolveThemeException.class);
        FXEngine.getContext().getApplicationConfiguration().setThemeName(themeMetaData.getName());
    }

    @Override
    public void changeTheme(String themeName) throws ResolveThemeException {
        ThemeMetaData themeMetaData = themesMetaData
                .find(data -> data.getName().equals(themeName))
                .orElseThrow(() -> new ThemeNotFoundException(localizationProvider.getLocalization().getConfiguration().getString("fxEngine.applicationManager.changeThemeUsingThemeName.themeNotFoundException", themeName)));

        controllers.forEach((id, controller) -> {
            Optional<ThemeLoadedData> targetLoadedData = themeLoadedData.find(data -> data.isAllow(controller.getClass(), themeMetaData.getName()));
            ResolvedThemeLocation resolvedThemeLocation;
            if (targetLoadedData.isPresent()) {
                engineLogProvider.getLogger().debug("fxEngine.applicationManager.theme.gettingThemeFromCache", themeName, controller.getClass().getSimpleName());
                resolvedThemeLocation = targetLoadedData.get().getResolvedThemeLocation();
            } else {
                resolvedThemeLocation = resolve(themeMetaData, controller);
                themeLoadedData.add(new ThemeLoadedData(controller.getClass(), themeMetaData.getName(), resolvedThemeLocation));
            }

            controller.applyTheme(resolvedThemeLocation);
        }, ResolveThemeException.class);
        FXEngine.getContext().getApplicationConfiguration().setThemeName(themeMetaData.getName());
    }

    @Override
    public <C extends Controller> void applyTheme(C controller) throws ResolveThemeException {
        ThemeMetaData themeMetaData = getThemeMetaData();
        Optional<ThemeLoadedData> targetLoadedData = themeLoadedData.find(data -> data.isAllow(controller.getClass(), themeMetaData.getName()));
        ResolvedThemeLocation resolvedThemeLocation;
        if (targetLoadedData.isPresent()) {
            engineLogProvider.getLogger().debug("fxEngine.applicationManager.theme.gettingThemeFromCache", themeMetaData.getName(), controller.getClass().getSimpleName());
            resolvedThemeLocation = targetLoadedData.get().getResolvedThemeLocation();
        } else {
            resolvedThemeLocation = resolve(themeMetaData, controller);
            themeLoadedData.add(new ThemeLoadedData(controller.getClass(), themeMetaData.getName(), resolvedThemeLocation));
        }

        controller.applyTheme(resolvedThemeLocation);
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

    @Preview
    public Localization getLocalization(Controller controller, Locale locale) {
        BaseLocalizationMetaData currentLanguageMetaData = getApplicationLanguageMetaData(locale);

        String relativePath = FXEngine.getContext().getBean(ControllerLocalizationResolver.class,
                currentLanguageMetaData,
                controller.getMetaData().internationalization()
        ).resolve();

        return FXEngine.getContext().getBean(LocalizationLoader.class, currentLanguageMetaData, relativePath).load();
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

    private static class ThemeLoadedData {
        private final Class<? extends Controller> controllerClass;
        private final String theme;
        private final ResolvedThemeLocation resolvedThemeLocation;

        private ThemeLoadedData(Class<? extends Controller> controllerClass, String theme, ResolvedThemeLocation resolvedThemeLocation) {
            this.controllerClass = controllerClass;
            this.theme = theme;
            this.resolvedThemeLocation = resolvedThemeLocation;
        }

        public boolean isAllow(Class<? extends Controller> controllerClass, String theme) {
            return this.controllerClass.equals(controllerClass) && this.theme.equals(theme);
        }

        public ResolvedThemeLocation getResolvedThemeLocation() {
            return resolvedThemeLocation;
        }
    }
}
