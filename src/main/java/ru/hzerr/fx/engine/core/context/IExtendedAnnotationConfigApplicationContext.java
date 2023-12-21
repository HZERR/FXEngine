package ru.hzerr.fx.engine.core.context;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigRegistry;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;
import ru.hzerr.collections.list.HList;
import ru.hzerr.fx.engine.configuration.application.*;
import ru.hzerr.fx.engine.configuration.logging.IReadOnlyLoggingConfiguration;
import ru.hzerr.fx.engine.core.entity.EntityLoader;
import ru.hzerr.fx.engine.core.entity.IApplicationManager;
import ru.hzerr.fx.engine.core.language.localization.ILocalizationProvider;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

import java.util.Optional;
import java.util.function.Supplier;

public interface IExtendedAnnotationConfigApplicationContext extends AnnotationConfigRegistry, BeanDefinitionRegistry, ConfigurableApplicationContext, ResourceLoader {

    // BEGIN FLAT
    IReadOnlyLoggingConfiguration getLoggingConfiguration();
    IClassLoaderProvider getClassLoaderProvider();
    EntityLoader getEntityLoader();
    IStructureConfiguration getStructureConfiguration();
    <T extends IStructureConfiguration> T getStructureConfigurationAs(Class<T> type);
    IResourceStructureConfiguration getResourceStructureConfiguration();
    <T extends IResourceStructureConfiguration> T getResourceStructureConfiguration(Class<T> type);
    ILogProvider getFXEngineLogProvider();
    ILogProvider getApplicationLogProvider();
    ISoftwareConfiguration getSoftwareConfiguration();
    IApplicationConfiguration getApplicationConfiguration();
    ILocalizationProvider getApplicationLocalizationProvider();
    ILocalizationProvider getEngineLocalizationProvider();
    // END FLAT

    // BEGIN НА ПЕРЕСМОТРЕ
    IApplicationManager getApplicationManager();
    Stage getStage();
    void setStage(Stage stage);
    Scene getScene();
    Window getOwner();
    HList<String> getScannedPackages();
    // END НА ПЕРЕСМОТРЕ

    // BEGIN EXTENDED METHOD CONTEXT
    <T> Optional<T> fetchBean(Class<T> requiredType);
    boolean containsBean(Class<?> beanClass);
    boolean noContainsBean(Class<?> beanClass);
    <T> T getBeanByQualifier(Class<T> requiredType) throws BeansException;
    <T> T getBeanByQualifier(String qualifier, Class<T> requiredType) throws BeansException;
    // END EXTENDED METHOD CONTEXT

    // BEGIN GENERIC CONTEXT
    <T> void registerBean(Class<T> beanClass, Object... constructorArgs);
    <T> void registerBean(@Nullable String beanName, Class<T> beanClass, Object... constructorArgs);
    <T> void registerBean(@Nullable String beanName, Class<T> beanClass, @Nullable Supplier<T> supplier, BeanDefinitionCustomizer... customizers);

    void refreshForAotProcessing(RuntimeHints runtimeHints);
    DefaultListableBeanFactory getDefaultListableBeanFactory();

    default <T> void registerBean(Class<T> beanClass, BeanDefinitionCustomizer... customizers) {
        registerBean(null, beanClass, null, customizers);
    }

    default <T> void registerBean(@Nullable String beanName, Class<T> beanClass, BeanDefinitionCustomizer... customizers) {
        registerBean(beanName, beanClass, null, customizers);
    }

    default <T> void registerBean(Class<T> beanClass, Supplier<T> supplier, BeanDefinitionCustomizer... customizers) {
        registerBean(null, beanClass, supplier, customizers);
    }

    // END GENERIC CONTEXT
}
