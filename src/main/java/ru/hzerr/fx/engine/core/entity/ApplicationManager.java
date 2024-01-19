package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.collections.list.HList;
import ru.hzerr.collections.map.HMap;
import ru.hzerr.collections.map.Type;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.Concurrent;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.annotation.as.EngineLogProvider;
import ru.hzerr.fx.engine.core.entity.exception.LanguagePackMetaDataNotFoundException;
import ru.hzerr.fx.engine.core.language.BaseLocalizationMetaData;
import ru.hzerr.fx.engine.core.language.EntityLocalizationMetaData;
import ru.hzerr.fx.engine.core.language.localization.EngineLoggingLocalizationProvider;
import ru.hzerr.fx.engine.core.language.localization.EntityLocalization;
import ru.hzerr.fx.engine.core.language.localization.EntityLocalizationLoader;
import ru.hzerr.fx.engine.core.language.localization.EntityLocalizationProvider;
import ru.hzerr.fx.engine.core.path.resolver.ControllerLocalizationResolver;
import ru.hzerr.fx.engine.core.theme.*;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

import java.util.Locale;
import java.util.Optional;

@Concurrent
@Registered
public class ApplicationManager implements IApplicationManager {

    private final HMap<String, Controller> controllers = HMap.create(Type.SYNCHRONIZED);
    private final HList<ThemeCache> themeLoadedData = HList.createProtectedList();
    private final HList<ThemeMetaData> themesMetaData = HList.createProtectedList();

    private EngineLoggingLocalizationProvider localizationProvider;

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
            controller.setLocalizationProvider(new EntityLocalizationProvider(getLocalization(controller, locale)));
            controller.onChangeLanguage(controller.getLocalizationProvider().getLocalization());
        });

        FXEngine.getContext().getApplicationConfiguration().setLocale(locale);
    }

    @Override
    public void changeTheme(Class<? extends ThemeMetaData> themeMetaDataClass) throws LoadThemeException {
        ThemeMetaData themeMetaData = themesMetaData
                .find(data -> data.getClass().equals(themeMetaDataClass))
                .orElseThrow(() -> new ThemeNotFoundException("Unknown themeMetaDataClass: " + themeMetaDataClass.getSimpleName()));

        controllers.forEach((id, controller) -> {
            Optional<ThemeCache> targetLoadedData = themeLoadedData.find(data -> data.isAllow(controller.getClass(), themeMetaData.getName()));
            LoadedThemeData loadedThemeData;
            if (targetLoadedData.isPresent()) {
                engineLogProvider.getLogger().debug("fxEngine.applicationManager.theme.gettingThemeFromCache", themeMetaData.getName(), controller.getClass().getSimpleName());
                loadedThemeData = targetLoadedData.get().getLoadedThemeData();
            } else {
                loadedThemeData = load(themeMetaData, controller);
                themeLoadedData.add(new ThemeCache(controller.getClass(), themeMetaData.getName(), loadedThemeData));
            }

            controller.applyTheme(loadedThemeData);
        }, LoadThemeException.class);
        FXEngine.getContext().getApplicationConfiguration().setThemeName(themeMetaData.getName());
    }

    @Override
    public void changeTheme(String themeName) throws LoadThemeException {
        ThemeMetaData themeMetaData = themesMetaData
                .find(data -> data.getName().equals(themeName))
                .orElseThrow(() -> new ThemeNotFoundException(localizationProvider.getLocalization().getConfiguration().getString("fxEngine.applicationManager.changeThemeUsingThemeName.themeNotFoundException", themeName)));

        controllers.forEach((id, controller) -> {
            Optional<ThemeCache> targetLoadedData = themeLoadedData.find(data -> data.isAllow(controller.getClass(), themeMetaData.getName()));
            LoadedThemeData loadedThemeData;
            if (targetLoadedData.isPresent()) {
                engineLogProvider.getLogger().debug("fxEngine.applicationManager.theme.gettingThemeFromCache", themeName, controller.getClass().getSimpleName());
                loadedThemeData = targetLoadedData.get().getLoadedThemeData();
            } else {
                loadedThemeData = load(themeMetaData, controller);
                themeLoadedData.add(new ThemeCache(controller.getClass(), themeMetaData.getName(), loadedThemeData));
            }

            controller.applyTheme(loadedThemeData);
        }, LoadThemeException.class);
        FXEngine.getContext().getApplicationConfiguration().setThemeName(themeMetaData.getName());
    }

    @Override
    public <C extends Controller> void applyTheme(C controller) throws LoadThemeException {
        ThemeMetaData themeMetaData = getThemeMetaData();
        Optional<ThemeCache> targetLoadedData = themeLoadedData.find(data -> data.isAllow(controller.getClass(), themeMetaData.getName()));
        LoadedThemeData loadedThemeData;
        if (targetLoadedData.isPresent()) {
            engineLogProvider.getLogger().debug("fxEngine.applicationManager.theme.gettingThemeFromCache", themeMetaData.getName(), controller.getClass().getSimpleName());
            loadedThemeData = targetLoadedData.get().getLoadedThemeData();
        } else {
            loadedThemeData = load(themeMetaData, controller);
            themeLoadedData.add(new ThemeCache(controller.getClass(), themeMetaData.getName(), loadedThemeData));
        }

        controller.applyTheme(loadedThemeData);
    }

    @Override
    public ThemeMetaData getThemeMetaData() {
        String name = FXEngine.getContext().getApplicationConfiguration().getThemeName();
        return themesMetaData.find(themeMetaData -> themeMetaData.getName().equals(name))
                .orElseThrow(() -> new ThemeNotFoundException("Theme '" + name + "' can't be found"));
    }

    @Override
    public <C extends Controller> LoadedThemeData load(ThemeMetaData themeMetaData, C controller) throws LoadThemeException {
        CSSLoader loader = FXEngine.getContext().getBean(CSSLoader.class, themeMetaData, controller);
        loader.setInitialLocation(FXEngine.getContext().getResourceStructureConfiguration().getThemePackage());
        return loader.resolve();
    }

    @Override
    public <C extends Controller> LoadedThemeData load(C controller) throws LoadThemeException {
        return load(getThemeMetaData(), controller);
    }

    private EntityLocalization getLocalization(Controller controller, Locale locale) {
        BaseLocalizationMetaData entityLocalizationMetaData = getEntityLocalizationMetaData(locale);

        String relativePath = FXEngine.getContext().getBean(ControllerLocalizationResolver.class,
                entityLocalizationMetaData,
                controller.getMetaData().internationalization()
        ).resolve();

        return FXEngine.getContext().getBean(EntityLocalizationLoader.class, entityLocalizationMetaData, relativePath).load();
    }

    private EntityLocalizationMetaData getEntityLocalizationMetaData(Locale locale) {
        for (EntityLocalizationMetaData metaData : FXEngine.getContext().getBeansOfType(EntityLocalizationMetaData.class).values()) {
            if (metaData.getLocale().equals(locale)) {
                return metaData;
            }
        }

        throw new LanguagePackMetaDataNotFoundException(localizationProvider.getLocalization().getConfiguration().getString(
                "fx.engine.controller.getApplicationLanguageMetaData.languageMetaDataNotFoundException", locale.getLanguage() + "/" + locale.getCountry()
        ));
    }

    @Deprecated(since = "1.1", forRemoval = true)
    private record ThemeCache(Class<? extends Controller> controllerClass, String theme,
                              LoadedThemeData loadedThemeData) {

        public boolean isAllow(Class<? extends Controller> controllerClass, String theme) {
            return this.controllerClass.equals(controllerClass) && this.theme.equals(theme);
        }

        public LoadedThemeData getLoadedThemeData() {
            return loadedThemeData;
        }
    }

    @EngineLogProvider
    public void setEngineLogProvider(ILogProvider engineLogProvider) {
        this.engineLogProvider = engineLogProvider;
    }

    @ru.hzerr.fx.engine.core.annotation.as.EngineLoggingLocalizationProvider
    public void setLocalizationProvider(EngineLoggingLocalizationProvider localizationProvider) {
        this.localizationProvider = localizationProvider;
    }
}
