package ru.hzerr.fx.engine.core.context.bean.factory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import ru.hzerr.fx.engine.configuration.application.IReadOnlyApplicationConfiguration;
import ru.hzerr.fx.engine.configuration.application.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.configuration.application.IStructureConfiguration;
import ru.hzerr.fx.engine.configuration.application.StructureInitializer;
import ru.hzerr.fx.engine.configuration.logging.IReadOnlyLoggingConfiguration;
import ru.hzerr.fx.engine.configuration.logging.ReadOnlyLoggingConfiguration;
import ru.hzerr.fx.engine.core.InitializationException;
import ru.hzerr.fx.engine.core.annotation.Registered;

import java.util.Map;
import java.util.Objects;

@Registered
public class BarricadeBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, IStructureConfiguration> structureConfigurationMap = beanFactory.getBeansOfType(IStructureConfiguration.class);
        if (structureConfigurationMap.isEmpty()) {
            throw new StructureConfigurationNotFoundException("A class inheriting the \"" + IStructureConfiguration.class.getSimpleName() + "\" interface was not found. Please implement it and set the @" + Registered.class.getSimpleName() + " annotation or the appropriate Spring annotation for it. Also check the 'basePackages' parameter that you pass to create the context");
        }

        IStructureConfiguration structureConfiguration = structureConfigurationMap.values().iterator().next();
        try {
            new StructureInitializer(structureConfiguration).initialize();
        } catch (InitializationException e) {
            throw new StructureConfigurationWrongException("Could not initialize the class '" + structureConfiguration.getClass().getSimpleName() + "' with the given configuration. " + e.getMessage());
        }

        Map<String, IResourceStructureConfiguration> resourceStructureConfigurationMap = beanFactory.getBeansOfType(IResourceStructureConfiguration.class);
        if (resourceStructureConfigurationMap.isEmpty()) {
            throw new ResourceStructureConfigurationNotFoundException("A class inheriting the \"" + IResourceStructureConfiguration.class.getSimpleName() + "\" interface was not found. Please implement it and set the @" + Registered.class.getSimpleName() + " annotation or the appropriate Spring annotation for it. Also check the 'basePackages' parameter that you pass to create the context");
        }

        Map<String, IReadOnlyApplicationConfiguration> readOnlyApplicationConfigurationMap = beanFactory.getBeansOfType(IReadOnlyApplicationConfiguration.class);
        if (readOnlyApplicationConfigurationMap.isEmpty()) {
            throw new ReadOnlyApplicationConfigurationNotFoundException("A class inheriting the \"" + IReadOnlyApplicationConfiguration.class.getSimpleName() + "\" interface was not found. Please implement it and set the @" + Registered.class.getSimpleName() + " annotation or the appropriate Spring annotation for it. Also check the 'basePackages' parameter that you pass to create the context");
        }

        IReadOnlyApplicationConfiguration readOnlyApplicationConfiguration = readOnlyApplicationConfigurationMap.values().iterator().next();
        if (Objects.isNull(readOnlyApplicationConfiguration.getLocale())) {
            throw new ReadOnlyApplicationConfigurationWrongException("The locale cannot be null");
        }

        if (StringUtils.isEmpty(readOnlyApplicationConfiguration.getThemeName())) {
            throw new ReadOnlyApplicationConfigurationWrongException("The theme name cannot be null/empty");
        }

        Map<String, IReadOnlyLoggingConfiguration> readOnlyLoggingConfigurationMap = beanFactory.getBeansOfType(IReadOnlyLoggingConfiguration.class);
        if (readOnlyLoggingConfigurationMap.isEmpty()) {
            throw new ReadOnlyLoggingConfigurationNotFoundException("A class inheriting the \"" + IReadOnlyLoggingConfiguration.class.getSimpleName() + "\" interface was not found. Consider also inheritance from class \"" + ReadOnlyLoggingConfiguration.class.getSimpleName() + "\". Please implement it and set the @" + Registered.class.getSimpleName() + " annotation or the appropriate Spring annotation for it. Also check the 'basePackages' parameter that you pass to create the context");
        }
    }
}
