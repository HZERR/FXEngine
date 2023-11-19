package ru.hzerr.fx.engine.configuration.application.naming.strategy;

public class ApplicationConfigurationNamingStrategy implements IApplicationConfigurationNamingStrategy {

    @Override
    public String localeCountry() {
        return "application.locale.country";
    }

    @Override
    public String localeLanguage() {
        return "application.locale.language";
    }

    @Override
    public String localeVariant() {
        return "application.locale.variant";
    }

    @Override
    public String themeName() {
        return "application.theme.name";
    }
}
