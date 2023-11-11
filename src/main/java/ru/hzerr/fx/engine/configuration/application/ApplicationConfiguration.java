package ru.hzerr.fx.engine.configuration.application;

import org.apache.commons.configuration2.PropertiesConfiguration;
import ru.hzerr.fx.engine.annotation.Include;
import ru.hzerr.fx.engine.annotation.MetaData;
import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.configuration.application.naming.strategy.ApplicationConfigurationNamingStrategy;
import ru.hzerr.fx.engine.configuration.application.naming.strategy.IApplicationConfigurationNamingStrategy;

import java.util.Locale;

@Registered
public class ApplicationConfiguration implements IApplicationConfiguration {

    private final PropertiesConfiguration configuration;
    private final IReadOnlyApplicationConfiguration readOnlyApplicationConfiguration;
    private final IApplicationConfigurationNamingStrategy namingStrategy = new ApplicationConfigurationNamingStrategy();

    @Include
    public ApplicationConfiguration(@MetaData("applicationFileBasedConfiguration") PropertiesConfiguration configuration, IReadOnlyApplicationConfiguration readOnlyApplicationConfiguration) {
        this.readOnlyApplicationConfiguration = readOnlyApplicationConfiguration;
        this.configuration = configuration;
    }

    @Override
    public Locale getLocale() {
        return configuration.get(Locale.class, namingStrategy.locale(), readOnlyApplicationConfiguration.getLocale());
    }

    @Override
    public void setLocale(Locale locale) {
        configuration.setProperty(namingStrategy.locale(), readOnlyApplicationConfiguration.getLocale());
    }
}
