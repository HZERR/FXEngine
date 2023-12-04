package ru.hzerr.fx.engine.core.context;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
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
import ru.hzerr.collections.list.HList;
import ru.hzerr.fx.engine.configuration.application.*;
import ru.hzerr.fx.engine.core.ApplicationManager;
import ru.hzerr.fx.engine.core.BeanAlreadyExistsException;
import ru.hzerr.fx.engine.core.annotation.Redefinition;
import ru.hzerr.fx.engine.core.entity.EntityLoader;
import ru.hzerr.fx.engine.core.entity.IApplicationManager;
import ru.hzerr.fx.engine.core.language.localization.ILocalizationProvider;
import ru.hzerr.fx.engine.logging.factory.FXApplicationLogProvider;
import ru.hzerr.fx.engine.logging.factory.FXEngineLogProvider;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.function.Supplier;

public class ExtendedAnnotationConfigApplicationContext extends AnnotationConfigApplicationContext implements IExtendedAnnotationConfigApplicationContext {

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

    private final HList<String> basePackages;

    public ExtendedAnnotationConfigApplicationContext(@NotNull String... basePackages) {
        super(basePackages);
        this.basePackages = HList.of(basePackages);
    }

    // ======================================== BEGIN FLAT ACCESS ========================================

    public void setEngineLocalizationProvider(ILocalizationProvider engineLocalizationProvider) {
        this.engineLocalizationProvider = engineLocalizationProvider;
    }

    public void setEngineLogProvider(ILogProvider engineLogProvider) {
        this.engineLogProvider = engineLogProvider;
    }

    @Override
    public IClassLoaderProvider getClassLoaderProvider() { return getBean(IClassLoaderProvider.class); }

    @Override
    public EntityLoader getEntityLoader() {
        return getBean(EntityLoader.class);
    }

    @Override
    public IStructureConfiguration getStructureConfiguration() {
        return getBean(IStructureConfiguration.class);
    }

    @Override
    public IResourceStructureConfiguration getResourceStructureConfiguration() { return getBean(IResourceStructureConfiguration.class); }

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
    public ILocalizationProvider getApplicationLocalizationProvider() { return getBean(APPLICATION_LOGGING_LOCALIZATION_PROVIDER_BEAN_NAME, ILocalizationProvider.class); }

    @Override
    public ILocalizationProvider getEngineLocalizationProvider() { return getBean(ENGINE_LOGGING_LOCALIZATION_PROVIDER_BEAN_NAME, ILocalizationProvider.class); }

    // ======================================== END FLAT ACCESS ========================================

    @Override
    public HList<String> getScannedPackages() {
        return basePackages;
    }

    @Override
    public IApplicationManager getApplicationManager() {
        if (!containsBean(APPLICATION_MANAGER_BEAN_NAME)) {
            registerBean(APPLICATION_MANAGER_BEAN_NAME, ApplicationManager.class);
        }

        return getBean(APPLICATION_MANAGER_BEAN_NAME, IApplicationManager.class);
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Scene getScene() {
        return stage.getScene();
    }

    @Override
    public Window getOwner() {
        return stage.getOwner();
    }

    @Override
    public <T> Optional<T> fetchBean(Class<T> requiredType) {
        return containsBean(requiredType) ?
                Optional.of(getBean(requiredType)) :
                Optional.empty();
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
            throw new IllegalArgumentException(getEngineLocalizationProvider()
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
