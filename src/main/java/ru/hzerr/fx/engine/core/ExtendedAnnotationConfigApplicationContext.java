package ru.hzerr.fx.engine.core;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import ru.hzerr.fx.engine.configuration.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.configuration.ISoftwareConfiguration;
import ru.hzerr.fx.engine.configuration.IStructureConfiguration;
import ru.hzerr.fx.engine.configuration.StructureInitializer;
import ru.hzerr.fx.engine.configuration.application.IApplicationConfiguration;
import ru.hzerr.fx.engine.configuration.logging.ILoggingConfiguration;
import ru.hzerr.fx.engine.core.entity.IApplicationManager;
import ru.hzerr.fx.engine.core.language.*;
import ru.hzerr.fx.engine.core.language.localization.ILocalizationProvider;
import ru.hzerr.fx.engine.core.language.localization.LocalizationProvider;
import ru.hzerr.fx.engine.core.path.*;
import ru.hzerr.fx.engine.logging.ConfigurableException;
import ru.hzerr.fx.engine.logging.factory.FXApplicationLogProvider;
import ru.hzerr.fx.engine.logging.factory.FXEngineLogProvider;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

import java.lang.annotation.Annotation;

public class ExtendedAnnotationConfigApplicationContext extends AnnotationConfigApplicationContext {

    private ILogProvider engineLogProvider;

    public ExtendedAnnotationConfigApplicationContext() {
        super();
        prepareStructureConfiguration();
        prepareLoggingInternationalization();
        prepareLogProvider();
        setAllowBeanDefinitionOverriding(true);
    }

    public ExtendedAnnotationConfigApplicationContext(DefaultListableBeanFactory beanFactory) {
        super(beanFactory);
        prepareStructureConfiguration();
        prepareLoggingInternationalization();
        prepareLogProvider();
        setAllowBeanDefinitionOverriding(true);
    }

    public ExtendedAnnotationConfigApplicationContext(Class<?>... componentClasses) {
        super(componentClasses);
        prepareStructureConfiguration();
        prepareLoggingInternationalization();
        prepareLogProvider();
        setAllowBeanDefinitionOverriding(true);
    }

    public ExtendedAnnotationConfigApplicationContext(String... basePackages) {
        super(basePackages);
        prepareStructureConfiguration();
        prepareLoggingInternationalization();
        prepareLogProvider();
        setAllowBeanDefinitionOverriding(true);
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
            prepareApplicationLoggingLanguagePack();
        } catch (ApplicationLoggingLanguageMetaDataNotFoundException | EngineLoggingLanguageMetaDataNotFoundException e) {
            throw new ApplicationContextInitializationException("Unable to create ApplicationContext. An error occurred while configuring the internationalization of the application", e);
        }
    }

    private void prepareLogProvider() {
        try {
            registerBean("applicationLogProvider", FXApplicationLogProvider.class);
            registerBean("engineLogProvider", FXEngineLogProvider.class);
            engineLogProvider = getBean("engineLogProvider", FXEngineLogProvider.class);
            engineLogProvider.configure();
        } catch (ConfigurableException e) {
            throw new ApplicationContextInitializationException("Unable to create ApplicationContext. A logger configuration error has occurred", e);
        }
    }

    public IStructureConfiguration getStructureConfiguration() {
        return getBean(IStructureConfiguration.class);
    }

    public IResourceStructureConfiguration getResourceStructureConfiguration() {
        return getBean(IResourceStructureConfiguration.class);
    }

    public ILogProvider getFXEngineLogProvider() {
        return getBean("engineLogProvider", FXEngineLogProvider.class);
    }
    public ILogProvider getApplicationLogProvider() {
        return getBean("applicationLogProvider", FXApplicationLogProvider.class);
    }

    public ISoftwareConfiguration getSoftwareConfiguration() {
        return getBeanByQualifier("softwareConfiguration", ISoftwareConfiguration.class);
    }

    public IApplicationManager getApplicationManager() {
        return getBean(IApplicationManager.class);
    }

    public IApplicationConfiguration getApplicationConfiguration() {
        return getBean(IApplicationConfiguration.class);
    }

    public ILocalizationProvider getEngineLocalizationProvider() {
        return getBean("engineLoggingLocalizationProvider", ILocalizationProvider.class);
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
            throw new IllegalArgumentException(getEngineLocalizationProvider().getLocalization().getConfiguration().getString(
                    "fxEngine.applicationContext.getBeanByQualifier.qualifierAnnotationNotFoundException", requiredType.getSimpleName()
            ));
    }

    public <T> T getBeanByQualifier(String qualifier, Class<T> requiredType) throws BeansException {
        engineLogProvider.getLogger().debug("fxEngine.applicationContext.getBeanByQualifier.getByQualifier", requiredType.getSimpleName(), qualifier);
        return BeanFactoryAnnotationUtils.qualifiedBeanOfType(getBeanFactory(), requiredType, qualifier);
    }

    private void prepareEngineLoggingLocalization() throws EngineLoggingLanguageMetaDataNotFoundException {
        Localization engineLoggingLocalization = new LanguagePackLoader(
                getEngineLoggingLanguageMetaData(),
                getEngineLoggingLanguageMetaData().getILocation().getLocation()
        ).load();

        registerBean("engineLoggingLocalizationProvider", LocalizationProvider.class, engineLoggingLocalization);
    }

    private void prepareApplicationLoggingLanguagePack() throws ApplicationLoggingLanguageMetaDataNotFoundException {
        ILoggingConfiguration applicationLoggingConfiguration = getBean(ILoggingConfiguration.class);
        String applicationLanguagePackageLocation = LocationTools.resolve(
                ResolvableLocation.of(
                        getBean(IResourceStructureConfiguration.class).getApplicationLoggingInternationalizationPackage(),
                        NullSafeResolveLocationOptions.THROW_EXCEPTION
                ),
                ResolvableLocation.of(
                        getApplicationLoggingLanguageMetaData().getILocation(),
                        NullSafeResolveLocationOptions.INSERT_EVERYWHERE
                ),
                SeparatorResolveLocationOptions.INSERT_EVERYWHERE,
                Separator.SLASH_SEPARATOR
        );

        String applicationLanguagePackLocation = LocationTools.resolve(
                ResolvableLocation.of(
                        applicationLanguagePackageLocation,
                        NullSafeResolveLocationOptions.THROW_EXCEPTION
                ),
                ResolvableLocation.of(
                        applicationLoggingConfiguration.getApplicationLoggingLanguageFileName(),
                        NullSafeResolveLocationOptions.THROW_EXCEPTION
                ),
                SeparatorResolveLocationOptions.INSERT_START,
                Separator.SLASH_SEPARATOR
        );

        Localization applicationLoggingLocalization = new LanguagePackLoader(
                getApplicationLoggingLanguageMetaData(),
                applicationLanguagePackLocation
        ).load();

        registerBean("applicationLoggingLocalizationProvider", LocalizationProvider.class, applicationLoggingLocalization);
    }

    private EngineLoggingLocalizationMetaData getEngineLoggingLanguageMetaData() throws EngineLoggingLanguageMetaDataNotFoundException {
        if (containsBean("engineLoggingLanguageMetaData"))
            return getBean("engineLoggingLanguageMetaData", EngineLoggingLocalizationMetaData.class);

        ILoggingConfiguration configuration = getBean(ILoggingConfiguration.class);
        for(EngineLoggingLocalizationMetaData metaData: getBeansOfType(EngineLoggingLocalizationMetaData.class).values()) {
            if (metaData.getLocale().equals(configuration.getEngineLocale())) {
                registerBean("engineLoggingLanguageMetaData", EngineLoggingLocalizationMetaData.class, () -> metaData);
                return metaData;
            }
        }

        throw new EngineLoggingLanguageMetaDataNotFoundException("The metadata of the language pack of the engine with the locale \"" + configuration.getEngineLocale().toString() + "\" was not found");
    }

    private LoggingLocalizationMetaData getApplicationLoggingLanguageMetaData() throws ApplicationLoggingLanguageMetaDataNotFoundException {
        if (containsBean("applicationLoggingLanguageMetaData"))
            return getBean("applicationLoggingLanguageMetaData", LoggingLocalizationMetaData.class);

        ILoggingConfiguration configuration = getBean(ILoggingConfiguration.class);
        for(LoggingLocalizationMetaData metaData: getBeansOfType(LoggingLocalizationMetaData.class).values()) {
            if (metaData.getLocale().equals(configuration.getEngineLocale())) {
                registerBean("applicationLoggingLanguageMetaData", LoggingLocalizationMetaData.class, () -> metaData);
                return metaData;
            }
        }

        throw new ApplicationLoggingLanguageMetaDataNotFoundException("The metadata of the language pack for debugging the application with the language locale \"" + configuration.getEngineLocale().toString() + "\" was not found");
    }
}
