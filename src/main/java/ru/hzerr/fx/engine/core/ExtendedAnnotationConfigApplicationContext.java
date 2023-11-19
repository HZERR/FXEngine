package ru.hzerr.fx.engine.core;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import ru.hzerr.collections.list.ArrayHList;
import ru.hzerr.collections.list.HList;
import ru.hzerr.fx.engine.configuration.application.*;
import ru.hzerr.fx.engine.configuration.logging.ILoggingConfiguration;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.annotation.Redefinition;
import ru.hzerr.fx.engine.core.entity.ApplicationManager;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.entity.IApplicationManager;
import ru.hzerr.fx.engine.core.language.*;
import ru.hzerr.fx.engine.core.language.localization.ILocalizationProvider;
import ru.hzerr.fx.engine.core.language.localization.LocalizationProvider;
import ru.hzerr.fx.engine.core.path.resolver.ApplicationLoggingLocalizationResolver;
import ru.hzerr.fx.engine.core.path.resolver.Resolver;
import ru.hzerr.fx.engine.core.theme.Theme;
import ru.hzerr.fx.engine.core.theme.ThemeLoader;
import ru.hzerr.fx.engine.core.theme.ThemeMetaData;
import ru.hzerr.fx.engine.logging.ConfigurableException;
import ru.hzerr.fx.engine.logging.factory.FXApplicationLogProvider;
import ru.hzerr.fx.engine.logging.factory.FXEngineLogProvider;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

import java.lang.annotation.Annotation;
import java.util.function.Supplier;

public class ExtendedAnnotationConfigApplicationContext extends AnnotationConfigApplicationContext {

    public static final String APPLICATION_LOGGING_LOCALIZATION_META_DATA_BEAN_NAME = "applicationLoggingLocalizationMetaData";
    public static final String ENGINE_LOGGING_LOCALIZATION_META_DATA_BEAN_NAME = "engineLoggingLocalizationMetaData";
    public static final String ENGINE_LOGGING_LOCALIZATION_PROVIDER_BEAN_NAME = "engineLoggingLocalizationProvider";
    public static final String APPLICATION_LOGGING_LOCALIZATION_PROVIDER_BEAN_NAME = "applicationLoggingLocalizationProvider";
    public static final String APPLICATION_MANAGER_BEAN_NAME = "applicationManager";
    public static final String SOFTWARE_CONFIGURATION_BEAN_NAME = "softwareConfiguration";
    public static final String APPLICATION_LOG_PROVIDER_BEAN_NAME = "applicationLogProvider";
    public static final String ENGINE_LOG_PROVIDER_BEAN_NAME = "engineLogProvider";
    private ILogProvider engineLogProvider;
    private ILocalizationProvider engineLocalizationProvider;
    private Stage stage;

    private final HList<Class<? extends Controller>> controllers = ArrayHList.create();
    private final HList<String> basePackages;

    public ExtendedAnnotationConfigApplicationContext(String... basePackages) {
        super(basePackages);
        prepareStructureConfiguration();
        prepareLoggingInternationalization();
        prepareLogProvider();
        this.basePackages = HList.of(basePackages);
        registerControllers();
        prepareThemes();
    }

    public HList<Class<? extends Controller>> getRegisteredControllers() {
        return controllers;
    }

    private void prepareThemes() {
        for (ThemeMetaData themeMetaData : getBeansOfType(ThemeMetaData.class).values()) {
            try {
                registerBean(ThemeLoader.class, themeMetaData, getRegisteredControllers());
                ThemeLoader themeLoader = getBean(ThemeLoader.class);
                themeLoader.setResourceLoader(getClassLoader());
                themeLoader.setInitialLocation(getResourceStructureConfiguration().getThemePackage());
                Theme theme = themeLoader.load();
                getApplicationManager().register(theme);
            } catch (LoadException le) {
                throw new ApplicationContextInitializationException("Unable to create ApplicationContext. An error occurred while loading the '" + themeMetaData.getName() + "' theme", le);
            }
        }
    }

    private void registerControllers() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setParallel(true)
                .forPackages(basePackages.toArray(String.class)));

        for (Class<? extends Controller> controllerClass : reflections.getSubTypesOf(Controller.class)) {
            if (controllerClass.isAnnotationPresent(FXEntity.class)) {
                controllers.add(controllerClass);
                engineLogProvider.getLogger().debug("fxEngine.applicationContext.registerControllers.controllerSuccessfullyRegistered", controllerClass.getSimpleName());
            }
        }
    }

    private void prepareStructureConfiguration() {
        try {
            getBean(StructureInitializer.class).initialize();
        } catch (InitializationException e) {
            throw new ApplicationContextInitializationException("Unable to create ApplicationContext. An error occurred while configuring the application structure", e);
        }
    }

    private void prepareLoggingInternationalization() {
        try {
            prepareEngineLoggingLocalization();
            prepareApplicationLoggingLocalization();
            engineLocalizationProvider = super.getBean(ENGINE_LOGGING_LOCALIZATION_PROVIDER_BEAN_NAME, LocalizationProvider.class);
        } catch (ApplicationLoggingLanguageMetaDataNotFoundException | EngineLoggingLanguageMetaDataNotFoundException e) {
            throw new ApplicationContextInitializationException("Unable to create ApplicationContext. An error occurred while configuring the internationalization of the application", e);
        }
    }

    private void prepareLogProvider() {
        try {
            registerBean(APPLICATION_LOG_PROVIDER_BEAN_NAME, FXApplicationLogProvider.class);
            registerBean(ENGINE_LOG_PROVIDER_BEAN_NAME, FXEngineLogProvider.class);
            engineLogProvider = getBean(ENGINE_LOG_PROVIDER_BEAN_NAME, FXEngineLogProvider.class);
            engineLogProvider.configure();
        } catch (ConfigurableException e) {
            throw new ApplicationContextInitializationException("Unable to create ApplicationContext. A logger configuration error has occurred", e);
        }
    }

    // ======================================== BEGIN FLAT ACCESS ========================================

    public IStructureConfiguration getStructureConfiguration() {
        return getBean(IStructureConfiguration.class);
    }

    public IResourceStructureConfiguration getResourceStructureConfiguration() {
        return getBean(IResourceStructureConfiguration.class);
    }

    public ILogProvider getFXEngineLogProvider() {
        return getBean(ENGINE_LOG_PROVIDER_BEAN_NAME, FXEngineLogProvider.class);
    }

    public ILogProvider getApplicationLogProvider() {
        return getBean(APPLICATION_LOG_PROVIDER_BEAN_NAME, FXApplicationLogProvider.class);
    }

    public ISoftwareConfiguration getSoftwareConfiguration() {
        return getBeanByQualifier(SOFTWARE_CONFIGURATION_BEAN_NAME, ISoftwareConfiguration.class);
    }

    public IApplicationConfiguration getApplicationConfiguration() {
        return getBean(IApplicationConfiguration.class);
    }

    public ILocalizationProvider getApplicationLocalizationProvider() {
        return getBean(APPLICATION_LOGGING_LOCALIZATION_PROVIDER_BEAN_NAME, ILocalizationProvider.class);
    }

    public ILocalizationProvider getEngineLocalizationProvider() {
        return getBean(ENGINE_LOGGING_LOCALIZATION_PROVIDER_BEAN_NAME, ILocalizationProvider.class);
    }

    // ======================================== END FLAT ACCESS ========================================

    public IApplicationManager getApplicationManager() {
        if (!containsBean(APPLICATION_MANAGER_BEAN_NAME)) {
            registerBean(APPLICATION_MANAGER_BEAN_NAME, ApplicationManager.class);
        }

        return getBean(APPLICATION_MANAGER_BEAN_NAME, IApplicationManager.class);
    }

    private void prepareEngineLoggingLocalization() throws EngineLoggingLanguageMetaDataNotFoundException {
        Localization engineLoggingLocalization = LocalizationLoader.from(getEngineLoggingLanguageMetaData(), getEngineLoggingLanguageMetaData().getILocation().getLocation()).load();

        registerBean(ENGINE_LOGGING_LOCALIZATION_PROVIDER_BEAN_NAME, LocalizationProvider.class, engineLoggingLocalization);
    }

    private void prepareApplicationLoggingLocalization() throws ApplicationLoggingLanguageMetaDataNotFoundException {
        super.register(ApplicationLoggingLocalizationResolver.class);
        Resolver applicationLocalizationResolver = getBean(ApplicationLoggingLocalizationResolver.class);

        Localization applicationLoggingLocalization = LocalizationLoader.from(getApplicationLoggingLanguageMetaData(), applicationLocalizationResolver.resolve()).load();

        registerBean(APPLICATION_LOGGING_LOCALIZATION_PROVIDER_BEAN_NAME, LocalizationProvider.class, applicationLoggingLocalization);
    }

    private EngineLoggingLocalizationMetaData getEngineLoggingLanguageMetaData() throws EngineLoggingLanguageMetaDataNotFoundException {
        if (containsBean(ENGINE_LOGGING_LOCALIZATION_META_DATA_BEAN_NAME))
            return getBean(ENGINE_LOGGING_LOCALIZATION_META_DATA_BEAN_NAME, EngineLoggingLocalizationMetaData.class);

        ILoggingConfiguration configuration = getBean(ILoggingConfiguration.class);
        for (EngineLoggingLocalizationMetaData metaData : getBeansOfType(EngineLoggingLocalizationMetaData.class).values()) {
            if (metaData.getLocale().equals(configuration.getEngineLocale())) {
                registerBean(ENGINE_LOGGING_LOCALIZATION_META_DATA_BEAN_NAME, EngineLoggingLocalizationMetaData.class, () -> metaData);
                return metaData;
            }
        }

        throw new EngineLoggingLanguageMetaDataNotFoundException("The metadata of the language pack of the engine with the locale '" + configuration.getEngineLocale().toString() + "' was not found");
    }

    private LoggingLocalizationMetaData getApplicationLoggingLanguageMetaData() throws ApplicationLoggingLanguageMetaDataNotFoundException {
        if (containsBean(APPLICATION_LOGGING_LOCALIZATION_META_DATA_BEAN_NAME))
            return getBean(APPLICATION_LOGGING_LOCALIZATION_META_DATA_BEAN_NAME, LoggingLocalizationMetaData.class);

        ILoggingConfiguration configuration = getBean(ILoggingConfiguration.class);
        for (LoggingLocalizationMetaData metaData : getBeansOfType(LoggingLocalizationMetaData.class).values()) {
            if (metaData.getLocale().equals(configuration.getEngineLocale())) {
                registerBean(APPLICATION_LOGGING_LOCALIZATION_META_DATA_BEAN_NAME, LoggingLocalizationMetaData.class, () -> metaData);
                return metaData;
            }
        }

        throw new ApplicationLoggingLanguageMetaDataNotFoundException("The metadata of the language pack for debugging the application with the language locale \"" + configuration.getEngineLocale().toString() + "\" was not found");
    }

    public boolean containsBean(Class<?> beanClass) {
        return !noContainsBean(beanClass);
    }

    public boolean noContainsBean(Class<?> beanClass) {
        return getBeansOfType(beanClass).isEmpty();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return stage.getScene();
    }

    public <T> T getBeanByQualifier(Class<T> requiredType) throws BeansException {
        engineLogProvider.getLogger().debug("fxEngine.applicationContext.getBeanByQualifier.started", requiredType.getSimpleName());
        Qualifier metaData = AnnotationUtils.findAnnotation(requiredType, Qualifier.class);
        if (metaData != null) {
            if (StringUtils.isEmpty(metaData.value())) {
                engineLogProvider.getLogger().debug("fxEngine.applicationContext.getBeanByQualifier.annotationWasNotFound.continuationSearchInChildAnnotation", requiredType.getSimpleName(), Qualifier.class.getSimpleName());
                for (MergedAnnotation<Annotation> annotation : MergedAnnotations.from(requiredType)) {
                    if (annotation.getType().isAnnotationPresent(Qualifier.class)) {
                        String value = annotation.getString("value");
                        if (StringUtils.isNotEmpty(value)) {
                            engineLogProvider.getLogger().debug("fxEngine.applicationContext.getBeanByQualifier.getByNameOrQualifier", requiredType.getSimpleName(), value);
                            return getBean(value, requiredType);
                        }
                    }
                }

                throw new IllegalArgumentException(getBean("engineLoggingLocalizationProvider", ILocalizationProvider.class).getLocalization().getConfiguration().getString(
                        "fxEngine.applicationContext.getBeanByQualifier.qualifierValueIsEmptyException", requiredType.getSimpleName()
                ));
            } else
                return getBeanByQualifier(metaData.value(), requiredType);
        } else
            throw new IllegalArgumentException(getEngineLocalizationProvider()
                    .getLocalization()
                    .getConfiguration()
                    .getString("fxEngine.applicationContext.getBeanByQualifier.qualifierAnnotationNotFoundException", requiredType.getSimpleName())
            );
    }

    public <T> T getBeanByQualifier(String qualifier, Class<T> requiredType) throws BeansException {
        engineLogProvider.getLogger().debug("fxEngine.applicationContext.getBeanByQualifier.getByQualifier", requiredType.getSimpleName(), qualifier);
        return BeanFactoryAnnotationUtils.qualifiedBeanOfType(getBeanFactory(), requiredType, qualifier);
    }

    @Override
    public void register(Class<?>... componentClasses) {
        for (Class<?> componentClass : componentClasses) {
            checkRedefinition(componentClass);
            super.register(componentClass);
        }
    }

    @Override
    public <T> void registerBean(Class<T> beanClass, Object... constructorArgs) {
        registerBean(null, beanClass, constructorArgs);
    }

    @Override
    public <T> void registerBean(String beanName, Class<T> beanClass, Object... constructorArgs) {
        checkRedefinition(beanClass);
        registerBean(beanName, beanClass, (Supplier<T>) null,
                bd -> {
                    for (Object arg : constructorArgs) {
                        bd.getConstructorArgumentValues().addGenericArgumentValue(arg);
                    }
                });
    }

    @Override
    public <T> void registerBean(String beanName, Class<T> beanClass, Supplier<T> supplier, BeanDefinitionCustomizer... customizers) {
        checkRedefinition(beanClass);
        super.registerBean(beanName, beanClass, supplier, customizers);
    }

    private void checkRedefinition(Class<?> componentClass) {
        if (componentClass != null) {
            if (componentClass.isAnnotationPresent(Redefinition.class)) {
                Redefinition redefinition = componentClass.getAnnotation(Redefinition.class);
                if (!redefinition.isRedefined()) {
                    if (containsBean(componentClass)) {
                        throw new BeanAlreadyExistsException(engineLocalizationProvider.getLocalization().getConfiguration().getString("fxEngine.applicationContext.register.beanAlreadyExistsException", componentClass.getSimpleName()));
                    }
                }
            }
        }
    }
}
