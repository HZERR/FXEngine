package ru.hzerr.fx.engine.configuration.application;

import org.apache.commons.configuration2.PropertiesConfiguration;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.MetaData;
import ru.hzerr.fx.engine.core.annotation.Registered;
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
        if (configuration.containsKey(namingStrategy.localeCountry())) {
            return Locale.of(configuration.getString(namingStrategy.localeLanguage()), configuration.getString(namingStrategy.localeCountry()), configuration.getString(namingStrategy.localeVariant()));
        }

        return readOnlyApplicationConfiguration.getLocale();
    }

    @Override
    public void setLocale(Locale locale) {
        configuration.setProperty(namingStrategy.localeLanguage(), locale.getLanguage());
        configuration.setProperty(namingStrategy.localeCountry(), locale.getCountry());
        configuration.setProperty(namingStrategy.localeVariant(), locale.getVariant());
    }

    @Override
    public String getThemeName() {
        if (configuration.containsKey(namingStrategy.themeName())) {
            return configuration.getString(namingStrategy.themeName());
        }

        return readOnlyApplicationConfiguration.getThemeName();
    }

    @Override
    public void setThemeName(String themeName) {
        configuration.setProperty(namingStrategy.themeName(), themeName);
    }
}
