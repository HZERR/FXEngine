package ru.hzerr.fx.engine.core;

import org.apache.commons.lang3.StringUtils;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.metrics.ApplicationStartup;
import ru.hzerr.fx.engine.configuration.IApplicationConfiguration;
import ru.hzerr.fx.engine.configuration.IStructureApplicationConfiguration;
import ru.hzerr.fx.engine.configuration.IStructureConfiguration;
import ru.hzerr.fx.engine.configuration.StructureInitializer;
import ru.hzerr.fx.engine.logging.ConfigurableException;
import ru.hzerr.fx.engine.logging.factory.FXApplicationLogProvider;
import ru.hzerr.fx.engine.logging.factory.FXEngineLogProvider;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Supplier;

public class ExtendedAnnotationConfigApplicationContext extends AnnotationConfigApplicationContext {

    private final ILogProvider engineLogProvider = BeanFactoryAnnotationUtils.qualifiedBeanOfType(getBeanFactory(), FXEngineLogProvider.class, "engineLogProvider");

    public ExtendedAnnotationConfigApplicationContext() {
        super();
        prepareStructureConfiguration();
        prepareLogProvider();
    }

    public ExtendedAnnotationConfigApplicationContext(DefaultListableBeanFactory beanFactory) {
        super(beanFactory);
        prepareStructureConfiguration();
        prepareLogProvider();
    }

    public ExtendedAnnotationConfigApplicationContext(Class<?>... componentClasses) {
        super(componentClasses);
        prepareStructureConfiguration();
        prepareLogProvider();
    }

    public ExtendedAnnotationConfigApplicationContext(String... basePackages) {
        super(basePackages);
        prepareStructureConfiguration();
        prepareLogProvider();
    }

    private void prepareStructureConfiguration() {
        try {
            getBean(StructureInitializer.class).initialize();
        } catch (InitializationException e) {
            throw new ApplicationContextInitializationException("Unable to create ApplicationContext. An error occurred while configuring the application structure", e);
        }
    }

    private void prepareLogProvider() {
        try {
            engineLogProvider.configure();
        } catch (ConfigurableException e) {
            throw new ApplicationContextInitializationException("Unable to create ApplicationContext. A logger configuration error has occurred", e);
        }
    }

    public IStructureConfiguration getStructureConfiguration() {
        return getBean(IStructureConfiguration.class);
    }

    public IStructureApplicationConfiguration getStructureApplicationConfiguration() {
        return getBean(IStructureApplicationConfiguration.class);
    }

    public ILogProvider getFXEngineLogProvider() {
        return getBeanByQualifier(FXEngineLogProvider.class);
    }
    public ILogProvider getApplicationLogProvider() {
        return getBeanByQualifier(FXApplicationLogProvider.class);
    }

    public IApplicationConfiguration getApplicationConfiguration() {
        return getBeanByQualifier("applicationConfiguration", IApplicationConfiguration.class);
    }

    public IApplicationConfiguration getBaseApplicationConfiguration() {
        return getBeanByQualifier("baseApplicationConfiguration", IApplicationConfiguration.class);
    }

    @Override
    public void setEnvironment(ConfigurableEnvironment environment) {
        super.setEnvironment(environment);
    }

    @Override
    public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
        super.setBeanNameGenerator(beanNameGenerator);
    }

    @Override
    public void setScopeMetadataResolver(ScopeMetadataResolver scopeMetadataResolver) {
        super.setScopeMetadataResolver(scopeMetadataResolver);
    }

    @Override
    public void register(Class<?>... componentClasses) {
        super.register(componentClasses);
    }

    @Override
    public void scan(String... basePackages) {
        super.scan(basePackages);
    }

    @Override
    public <T> void registerBean(String beanName, Class<T> beanClass, Supplier<T> supplier, BeanDefinitionCustomizer... customizers) {
        super.registerBean(beanName, beanClass, supplier, customizers);
    }

    @Override
    public void setParent(ApplicationContext parent) {
        super.setParent(parent);
    }

    @Override
    public void setApplicationStartup(ApplicationStartup applicationStartup) {
        super.setApplicationStartup(applicationStartup);
    }

    @Override
    public void setAllowBeanDefinitionOverriding(boolean allowBeanDefinitionOverriding) {
        super.setAllowBeanDefinitionOverriding(allowBeanDefinitionOverriding);
    }

    @Override
    public void setAllowCircularReferences(boolean allowCircularReferences) {
        super.setAllowCircularReferences(allowCircularReferences);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        super.setResourceLoader(resourceLoader);
    }

    @Override
    public Resource getResource(String location) {
        return super.getResource(location);
    }

    @Override
    public Resource[] getResources(String locationPattern) throws IOException {
        return super.getResources(locationPattern);
    }

    @Override
    public void setClassLoader(ClassLoader classLoader) {
        super.setClassLoader(classLoader);
    }

    @Override
    public ClassLoader getClassLoader() {
        return super.getClassLoader();
    }

    @Override
    protected void cancelRefresh(BeansException ex) {
        super.cancelRefresh(ex);
    }

    @Override
    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
        return super.getAutowireCapableBeanFactory();
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {
        super.registerBeanDefinition(beanName, beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        super.removeBeanDefinition(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        return super.getBeanDefinition(beanName);
    }

    @Override
    public boolean isBeanNameInUse(String beanName) {
        return super.isBeanNameInUse(beanName);
    }

    @Override
    public void registerAlias(String beanName, String alias) {
        super.registerAlias(beanName, alias);
    }

    @Override
    public void removeAlias(String alias) {
        super.removeAlias(alias);
    }

    @Override
    public boolean isAlias(String beanName) {
        return super.isAlias(beanName);
    }

    @Override
    public void refreshForAotProcessing(RuntimeHints runtimeHints) {
        super.refreshForAotProcessing(runtimeHints);
    }

    @Override
    public <T> void registerBean(Class<T> beanClass, Object... constructorArgs) {
        super.registerBean(beanClass, constructorArgs);
    }

    @Override
    public <T> void registerBean(String beanName, Class<T> beanClass, Object... constructorArgs) {
        super.registerBean(beanName, beanClass, constructorArgs);
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String getApplicationName() {
        return super.getApplicationName();
    }

    @Override
    public void setDisplayName(String displayName) {
        super.setDisplayName(displayName);
    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName();
    }

    @Override
    public ApplicationContext getParent() {
        return super.getParent();
    }

    @Override
    public ConfigurableEnvironment getEnvironment() {
        return super.getEnvironment();
    }

    @Override
    protected ConfigurableEnvironment createEnvironment() {
        return super.createEnvironment();
    }

    @Override
    public long getStartupDate() {
        return super.getStartupDate();
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        super.publishEvent(event);
    }

    @Override
    public void publishEvent(Object event) {
        super.publishEvent(event);
    }

    @Override
    protected void publishEvent(Object event, ResolvableType typeHint) {
        super.publishEvent(event, typeHint);
    }

    @Override
    public ApplicationStartup getApplicationStartup() {
        return super.getApplicationStartup();
    }

    @Override
    protected ResourcePatternResolver getResourcePatternResolver() {
        return super.getResourcePatternResolver();
    }

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        super.addBeanFactoryPostProcessor(postProcessor);
    }

    @Override
    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
        return super.getBeanFactoryPostProcessors();
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        super.addApplicationListener(listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        super.removeApplicationListener(listener);
    }

    @Override
    public Collection<ApplicationListener<?>> getApplicationListeners() {
        return super.getApplicationListeners();
    }

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        super.refresh();
    }

    @Override
    protected void prepareRefresh() {
        super.prepareRefresh();
    }

    @Override
    protected void initPropertySources() {
        super.initPropertySources();
    }

    @Override
    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        return super.obtainFreshBeanFactory();
    }

    @Override
    protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        super.prepareBeanFactory(beanFactory);
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        super.postProcessBeanFactory(beanFactory);
    }

    @Override
    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        super.invokeBeanFactoryPostProcessors(beanFactory);
    }

    @Override
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        super.registerBeanPostProcessors(beanFactory);
    }

    @Override
    protected void initMessageSource() {
        super.initMessageSource();
    }

    @Override
    protected void initApplicationEventMulticaster() {
        super.initApplicationEventMulticaster();
    }

    @Override
    protected void initLifecycleProcessor() {
        super.initLifecycleProcessor();
    }

    @Override
    protected void onRefresh() throws BeansException {
        super.onRefresh();
    }

    @Override
    protected void registerListeners() {
        super.registerListeners();
    }

    @Override
    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        super.finishBeanFactoryInitialization(beanFactory);
    }

    @Override
    protected void finishRefresh() {
        super.finishRefresh();
    }

    @Override
    protected void resetCommonCaches() {
        super.resetCommonCaches();
    }

    @Override
    public void registerShutdownHook() {
        super.registerShutdownHook();
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    protected void doClose() {
        super.doClose();
    }

    @Override
    protected void destroyBeans() {
        super.destroyBeans();
    }

    @Override
    protected void onClose() {
        super.onClose();
    }

    @Override
    public boolean isActive() {
        return super.isActive();
    }

    @Override
    protected void assertBeanFactoryActive() {
        super.assertBeanFactoryActive();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return super.getBean(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return super.getBean(name, requiredType);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return super.getBean(name, args);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return super.getBean(requiredType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
        return super.getBean(requiredType, args);
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

                throw new IllegalArgumentException("Необходимая аннотация 'Qualifier' присутствует в классе " + requiredType.getSimpleName() + ". " +
                        "Однако она имеет пустое значение и движок не может найти 'Child' аннотацию по отношению к Qualifier аннотации");
            } else
                return getBeanByQualifier(metaData.value(), requiredType);
        } else
            throw new IllegalArgumentException("Необходимая аннотация 'Qualifier' отсутствует в классе " + requiredType.getSimpleName());
    }

    public <T> T getBeanByQualifier(String qualifier, Class<T> requiredType) throws BeansException {
        engineLogProvider.getLogger().debug("fxEngine.applicationContext.getBeanByQualifier.getByQualifier", requiredType.getSimpleName(), qualifier);
        return BeanFactoryAnnotationUtils.qualifiedBeanOfType(getBeanFactory(), requiredType, qualifier);
    }

    @Override
    public <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType) {
        return super.getBeanProvider(requiredType);
    }

    @Override
    public <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType) {
        return super.getBeanProvider(requiredType);
    }

    @Override
    public boolean containsBean(String name) {
        return super.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return super.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return super.isPrototype(name);
    }

    @Override
    public boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
        return super.isTypeMatch(name, typeToMatch);
    }

    @Override
    public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
        return super.isTypeMatch(name, typeToMatch);
    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return super.getType(name);
    }

    @Override
    public Class<?> getType(String name, boolean allowFactoryBeanInit) throws NoSuchBeanDefinitionException {
        return super.getType(name, allowFactoryBeanInit);
    }

    @Override
    public String[] getAliases(String name) {
        return super.getAliases(name);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return super.containsBeanDefinition(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return super.getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return super.getBeanDefinitionNames();
    }

    @Override
    public <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType, boolean allowEagerInit) {
        return super.getBeanProvider(requiredType, allowEagerInit);
    }

    @Override
    public <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType, boolean allowEagerInit) {
        return super.getBeanProvider(requiredType, allowEagerInit);
    }

    @Override
    public String[] getBeanNamesForType(ResolvableType type) {
        return super.getBeanNamesForType(type);
    }

    @Override
    public String[] getBeanNamesForType(ResolvableType type, boolean includeNonSingletons, boolean allowEagerInit) {
        return super.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return super.getBeanNamesForType(type);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
        return super.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return super.getBeansOfType(type);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
        return super.getBeansOfType(type, includeNonSingletons, allowEagerInit);
    }

    @Override
    public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
        return super.getBeanNamesForAnnotation(annotationType);
    }

    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
        return super.getBeansWithAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) throws NoSuchBeanDefinitionException {
        return super.findAnnotationOnBean(beanName, annotationType);
    }

    @Override
    public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType, boolean allowFactoryBeanInit) throws NoSuchBeanDefinitionException {
        return super.findAnnotationOnBean(beanName, annotationType, allowFactoryBeanInit);
    }

    @Override
    public <A extends Annotation> Set<A> findAllAnnotationsOnBean(String beanName, Class<A> annotationType, boolean allowFactoryBeanInit) throws NoSuchBeanDefinitionException {
        return super.findAllAnnotationsOnBean(beanName, annotationType, allowFactoryBeanInit);
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return super.getParentBeanFactory();
    }

    @Override
    public boolean containsLocalBean(String name) {
        return super.containsLocalBean(name);
    }

    @Override
    protected BeanFactory getInternalParentBeanFactory() {
        return super.getInternalParentBeanFactory();
    }

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return super.getMessage(code, args, defaultMessage, locale);
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return super.getMessage(code, args, locale);
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return super.getMessage(resolvable, locale);
    }

    @Override
    protected MessageSource getInternalParentMessageSource() {
        return super.getInternalParentMessageSource();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public boolean isRunning() {
        return super.isRunning();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void addProtocolResolver(ProtocolResolver resolver) {
        super.addProtocolResolver(resolver);
    }

    @Override
    public Collection<ProtocolResolver> getProtocolResolvers() {
        return super.getProtocolResolvers();
    }

    @Override
    public <T> Map<Resource, T> getResourceCache(Class<T> valueType) {
        return super.getResourceCache(valueType);
    }

    @Override
    public void clearResourceCaches() {
        super.clearResourceCaches();
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return super.getResourceByPath(path);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
