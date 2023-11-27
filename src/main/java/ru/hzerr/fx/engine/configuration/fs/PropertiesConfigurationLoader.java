package ru.hzerr.fx.engine.configuration.fs;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.context.annotation.Bean;
import ru.hzerr.fx.engine.configuration.application.IStructureConfiguration;
import ru.hzerr.fx.engine.core.LoadException;
import ru.hzerr.fx.engine.core.Loader;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;

@Registered
public class PropertiesConfigurationLoader implements Loader<PropertiesConfiguration> {

    private FileBasedConfigurationBuilder<PropertiesConfiguration> builder;
    private String location;

    @Include
    public PropertiesConfigurationLoader(IStructureConfiguration configuration) {
        builder = new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                .configure(new Parameters().properties()
                        .setFile(configuration.getSoftwareConfigurationFile().asIOFile())
                        .setThrowExceptionOnMissing(true)
                        .setListDelimiterHandler(new DefaultListDelimiterHandler(';'))
                        .setIncludesAllowed(true));

        this.location = configuration.getSoftwareConfigurationFile().getLocation();
    }

    @Override
    @Bean("applicationFileBasedConfiguration")
    public PropertiesConfiguration load() throws LoadException {
        builder.setAutoSave(true);
        try {
            return builder.getConfiguration();
        } catch (ConfigurationException ce) {
            throw new LoadException("Unable to load a configuration on the path " + location, ce);
        }
    }
}
