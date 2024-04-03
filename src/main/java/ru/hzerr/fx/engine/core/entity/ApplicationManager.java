package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.fx.engine.configuration.application.IApplicationConfiguration;
import ru.hzerr.fx.engine.core.annotation.Concurrent;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.annotation.as.EngineLogProvider;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.entity.exception.LocalizationMetaDataNotFoundException;
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
import java.util.Objects;

@Concurrent
@Registered
public class ApplicationManager implements IApplicationManager {

    private IExtendedAnnotationConfigApplicationContext applicationContext;

    private IControllerManagedRepository controllerRepository;
    private IThemeManagedRepository themeRepository;

    private IApplicationConfiguration applicationConfiguration;
    private EngineLoggingLocalizationProvider localizationProvider;
    private ILogProvider engineLogProvider;

    @Override
    public void register(String id, Controller controller) {
        controllerRepository.register(id, controller);
    }

    @Override
    public void unregister(String id) {
        controllerRepository.unregister(id);
    }

    /**
     * Устанавливает язык приложения. Это обновит язык для всех зарегистрированных контроллеров и сохранит настройки в конфигурации.
     *
     * @param locale локаль, которую нужно установить в качестве языка приложения
     */
    @Override
    public void changeLanguage(Locale locale) {
        controllerRepository.processEveryone((id, controller) -> {
            EntityLocalizationProvider provider = new EntityLocalizationProvider(getLocalization(controller, locale));
            controller.setLocalizationProvider(provider);
            controller.onChangeLanguage(provider.getLocalization());
        });

        applicationConfiguration.setLocale(locale);
    }

    @Override
    public void changeTheme(Class<? extends ThemeMetaData> themeMetaDataClass) throws LoadThemeException {
        ThemeMetaData themeMetaData = applicationContext.findBean(themeMetaDataClass)
                .orElseThrow(() -> new ThemeNotFoundException(localizationProvider.getLocalization()
                        .getConfiguration()
                        .getString("fxEngine.applicationManager.changeThemeUsingThemeMetaData.themeNotFoundException")));

        controllerRepository.processEveryone((id, controller) -> applyTheme(controller, themeMetaData), LoadThemeException.class);

        applicationConfiguration.setThemeName(themeMetaData.getName());
    }

    @Override
    public void changeTheme(String themeName) throws LoadThemeException {
        ThemeMetaData themeMetaData = applicationContext.findBean(ThemeMetaData.class, metaData -> metaData.getName().equals(themeName))
                .orElseThrow(() -> new ThemeNotFoundException(localizationProvider.getLocalization()
                        .getConfiguration()
                        .getString("fxEngine.applicationManager.theme.themeByNameNotFoundException")));

        controllerRepository.processEveryone((id, controller) -> applyTheme(controller, themeMetaData), LoadThemeException.class);

        applicationConfiguration.setThemeName(themeMetaData.getName());
    }

    @Override
    public <C extends Controller>
    void applyTheme(C controller) throws LoadThemeException {
        ThemeMetaData themeMetaData = getThemeMetaData();

        applyTheme(controller, themeMetaData);
    }

    public <C extends Controller, T extends ThemeMetaData>
    void applyTheme(C controller, T themeMetaData) throws LoadThemeException {
        LoadedThemeData loadedThemeData = themeRepository.getFromCache(themeMetaData.getName(), controller.getClass());
        if (Objects.isNull(loadedThemeData)) {
            loadedThemeData = load(themeMetaData, controller);
            themeRepository.putInCache(themeMetaData.getName(), controller.getClass(), loadedThemeData);
        } else
            engineLogProvider.getLogger().debug("fxEngine.applicationManager.theme.gettingThemeFromCache",
                    themeMetaData.getName(), controller.getClass().getSimpleName());

        controller.applyTheme(loadedThemeData);
    }

    @Override
    public ThemeMetaData getThemeMetaData() {
        String name = applicationConfiguration.getThemeName();
        return applicationContext
                .findBean(ThemeMetaData.class, themeMetaData -> themeMetaData.getName().equals(name))
                .orElseThrow(() -> new ThemeNotFoundException(localizationProvider.getLocalization()
                        .getConfiguration()
                        .getString("fxEngine.applicationManager.theme.themeByNameNotFoundException", name)));
    }

    private <C extends Controller> LoadedThemeData load(ThemeMetaData themeMetaData, C controller) throws LoadThemeException {
        CSSLoader loader = applicationContext.getBean(CSSLoader.class, themeMetaData, controller);
        loader.setInitialLocation(applicationContext.getResourceStructureConfiguration().getThemePackage());
        return loader.resolve();
    }

    private EntityLocalization getLocalization(Controller controller, Locale locale) {
        BaseLocalizationMetaData entityLocalizationMetaData = getEntityLocalizationMetaData(locale);

        String relativePath = applicationContext.getBean(ControllerLocalizationResolver.class,
                entityLocalizationMetaData,
                controller.getMetaData().internationalization()
        ).resolve();

        return applicationContext.getBean(EntityLocalizationLoader.class, entityLocalizationMetaData, relativePath).load();
    }

    private EntityLocalizationMetaData getEntityLocalizationMetaData(Locale locale) {
        return applicationContext.findBean(EntityLocalizationMetaData.class, metaData -> metaData.getLocale().equals(locale))
                .orElseThrow(() -> new LocalizationMetaDataNotFoundException(localizationProvider.getLocalization().getConfiguration().getString(
                        "fxEngine.controller.getApplicationLanguageMetaData.languageMetaDataNotFoundException", locale.getLanguage() + "/" + locale.getCountry()
                )));
    }

    @Include
    private void setApplicationContext(IExtendedAnnotationConfigApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @EngineLogProvider
    private void setEngineLogProvider(ILogProvider engineLogProvider) {
        this.engineLogProvider = engineLogProvider;
    }

    @ru.hzerr.fx.engine.core.annotation.as.EngineLoggingLocalizationProvider
    private void setLocalizationProvider(EngineLoggingLocalizationProvider localizationProvider) {
        this.localizationProvider = localizationProvider;
    }

    @Include
    public void setApplicationConfiguration(IApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Include
    public void setControllerRepository(IControllerManagedRepository controllerRepository) {
        this.controllerRepository = controllerRepository;
    }

    @Include
    public void setThemeRepository(IThemeManagedRepository themeRepository) {
        this.themeRepository = themeRepository;
    }
}
