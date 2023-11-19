package ru.hzerr.fx.engine.core.context;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigRegistry;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;
import ru.hzerr.fx.engine.configuration.application.IApplicationConfiguration;
import ru.hzerr.fx.engine.configuration.application.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.configuration.application.ISoftwareConfiguration;
import ru.hzerr.fx.engine.configuration.application.IStructureConfiguration;
import ru.hzerr.fx.engine.core.entity.IApplicationManager;
import ru.hzerr.fx.engine.core.language.localization.ILocalizationProvider;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

import java.util.function.Supplier;

public interface IExtendedAnnotationConfigApplicationContext extends AnnotationConfigRegistry, BeanDefinitionRegistry, ConfigurableApplicationContext, ResourceLoader {

    // BEGIN FLAT
    IStructureConfiguration getStructureConfiguration();
    IResourceStructureConfiguration getResourceStructureConfiguration();
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
    // END НА ПЕРЕСМОТРЕ

    // BEGIN EXTENDED METHOD CONTEXT
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
