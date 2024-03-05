package ru.hzerr.fx.engine.core.context;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.metrics.StartupStep;
import org.springframework.util.Assert;
import ru.hzerr.collections.list.HList;
import ru.hzerr.fx.engine.configuration.application.*;
import ru.hzerr.fx.engine.configuration.environment.IFXEnvironment;
import ru.hzerr.fx.engine.configuration.logging.IReadOnlyLoggingConfiguration;
import ru.hzerr.fx.engine.core.BeanAlreadyExistsException;
import ru.hzerr.fx.engine.core.annotation.Redefinition;
import ru.hzerr.fx.engine.core.entity.IApplicationManager;
import ru.hzerr.fx.engine.core.entity.IEntityLoader;
import ru.hzerr.fx.engine.core.language.localization.ApplicationLoggingLocalizationProvider;
import ru.hzerr.fx.engine.core.language.localization.EngineLoggingLocalizationProvider;
import ru.hzerr.fx.engine.core.language.localization.ILocalizationProvider;
import ru.hzerr.fx.engine.logging.provider.FXApplicationLogProvider;
import ru.hzerr.fx.engine.logging.provider.FXEngineLogProvider;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ExtendedAnnotationConfigApplicationContext extends AnnotationConfigApplicationContext implements IExtendedAnnotationConfigApplicationContext {

    private final OrderedClassPathBeanDefinitionScanner scanner;

    public static final String APPLICATION_LOGGING_LOCALIZATION_META_DATA_BEAN_NAME = "applicationLoggingLocalizationMetaData";
    public static final String ENGINE_LOGGING_LOCALIZATION_META_DATA_BEAN_NAME = "engineLoggingLocalizationMetaData";
    public static final String ENGINE_LOGGING_LOCALIZATION_PROVIDER_BEAN_NAME = "engineLoggingLocalizationProvider";
    public static final String APPLICATION_LOGGING_LOCALIZATION_PROVIDER_BEAN_NAME = "applicationLoggingLocalizationProvider";
    public static final String APPLICATION_MANAGER_BEAN_NAME = "applicationManager";
    public static final String SOFTWARE_CONFIGURATION_BEAN_NAME = "softwareConfiguration";
    public static final String APPLICATION_LOG_PROVIDER_BEAN_NAME = "applicationLogProvider";
    public static final String ENGINE_LOG_PROVIDER_BEAN_NAME = "engineLogProvider";
    private ILogProvider engineLogProvider;
    private EngineLoggingLocalizationProvider engineLocalizationProvider;

    private final HList<String> basePackages;

    public ExtendedAnnotationConfigApplicationContext(@NotNull String... basePackages) {
        super();
        this.scanner = new OrderedClassPathBeanDefinitionScanner(this);
        scan(basePackages);
        refresh();
        engineLogProvider = getFXEngineLogProvider();
        engineLocalizationProvider = getEngineLoggingLocalizationProvider();
        this.basePackages = HList.of(basePackages);
    }

    @Override
    public void scan(String... basePackages) {
        Assert.notEmpty(basePackages, "At least one base package must be specified");
        StartupStep scanPackages = getApplicationStartup().start("spring.context.base-packages.scan")
                .tag("packages", () -> Arrays.toString(basePackages));
        this.scanner.scan(basePackages);
        scanPackages.end();
    }

    // ======================================== BEGIN FLAT ACCESS ========================================

    public void setEngineLocalizationProvider(EngineLoggingLocalizationProvider engineLocalizationProvider) {
        this.engineLocalizationProvider = engineLocalizationProvider;
    }

    public void setEngineLogProvider(ILogProvider engineLogProvider) {
        this.engineLogProvider = engineLogProvider;
    }

    @Override
    public IReadOnlyLoggingConfiguration getLoggingConfiguration() { return getBean(IReadOnlyLoggingConfiguration.class); }

    @Override
    public IClassLoaderProvider getClassLoaderProvider() { return getBean(IClassLoaderProvider.class); }

    @Override
    public IEntityLoader getEntityLoader() {
        return getBean(IEntityLoader.class);
    }

    @Override
    public IStructureConfiguration getStructureConfiguration() {
        return getBean(IStructureConfiguration.class);
    }

    @Override
    public <T extends IStructureConfiguration> T getStructureConfigurationAs(Class<T> type) {
        return getBean(type);
    }

    @Override
    public IResourceStructureConfiguration getResourceStructureConfiguration() { return getBean(IResourceStructureConfiguration.class); }

    @Override
    public <T extends IResourceStructureConfiguration> T getResourceStructureConfigurationAs(Class<T> type) {
        return getBean(type);
    }

    @Override
    public ILogProvider getFXEngineLogProvider() {
        return getBean(ENGINE_LOG_PROVIDER_BEAN_NAME, FXEngineLogProvider.class);
    }

    @Override
    public ILogProvider getApplicationLogProvider() {
        return getBean(APPLICATION_LOG_PROVIDER_BEAN_NAME, FXApplicationLogProvider.class);
    }

    @Override
    public ISoftwareConfiguration getSoftwareConfiguration() {
        return getBeanByQualifier(SOFTWARE_CONFIGURATION_BEAN_NAME, ISoftwareConfiguration.class);
    }

    @Override
    public IApplicationConfiguration getApplicationConfiguration() {
        return getBean(IApplicationConfiguration.class);
    }

    @Override
    public ApplicationLoggingLocalizationProvider getApplicationLoggingLocalizationProvider() {
        if (getLoggingConfiguration().isInternationalizationEnabled()) {
            return getBean(APPLICATION_LOGGING_LOCALIZATION_PROVIDER_BEAN_NAME, ApplicationLoggingLocalizationProvider.class);
        }

        throw new IllegalAccessBeanException(engineLocalizationProvider.getLocalization().getConfiguration().getString("fxEngine.applicationContext.getApplicationLocalizationProvider.illegalAccessBeanException"));
    }

    @Override
    public EngineLoggingLocalizationProvider getEngineLoggingLocalizationProvider() { return getBean(ENGINE_LOGGING_LOCALIZATION_PROVIDER_BEAN_NAME, EngineLoggingLocalizationProvider.class); }

    // ======================================== END FLAT ACCESS ========================================

    @Override
    public HList<String> getScannedPackages() {
        return basePackages;
    }

    @Override
    public <T> T registerAndGetBean(Class<T> beanClass, Object... constructorArgs) {
        if (noContainsBean(beanClass)) {
            registerBean(beanClass, constructorArgs);
        }

        return getBean(beanClass);
    }

    @Override
    public IApplicationManager getApplicationManager() {
        return getBean(APPLICATION_MANAGER_BEAN_NAME, IApplicationManager.class);
    }

    @Override
    public IFXEnvironment getFXEnvironment() {
        return getBean(IFXEnvironment.class);
    }

    @Override
    public <T> Optional<T> findBean(Class<T> requiredType) {
        return containsBean(requiredType) ?
                Optional.of(getBean(requiredType)) :
                Optional.empty();
    }

    @Override
    public <T> Optional<T> findBean(Class<T> requiredType, Predicate<T> predicate) {
        if (containsBean(requiredType)) {
            for (T bean: getBeansOfType(requiredType).values()) {
                if (predicate.test(bean)) {
                    return Optional.of(bean);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean containsBean(Class<?> beanClass) {
        return !noContainsBean(beanClass);
    }

    @Override
    public boolean noContainsBean(Class<?> beanClass) {
        return getBeansOfType(beanClass).isEmpty();
    }

    @Override
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
            throw new IllegalArgumentException(getEngineLoggingLocalizationProvider()
                    .getLocalization()
                    .getConfiguration()
                    .getString("fxEngine.applicationContext.getBeanByQualifier.qualifierAnnotationNotFoundException", requiredType.getSimpleName())
            );
    }

    @Override
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

    @Deprecated
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
