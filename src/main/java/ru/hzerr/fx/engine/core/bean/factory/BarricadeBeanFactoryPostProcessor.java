package ru.hzerr.fx.engine.core.bean.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import ru.hzerr.fx.engine.configuration.application.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.configuration.application.IStructureConfiguration;
import ru.hzerr.fx.engine.configuration.application.StructureInitializer;
import ru.hzerr.fx.engine.core.InitializationException;
import ru.hzerr.fx.engine.core.annotation.Registered;

import java.util.Map;

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
    }
}
