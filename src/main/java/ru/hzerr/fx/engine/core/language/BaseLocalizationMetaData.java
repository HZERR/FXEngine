package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.ConfigSyntax;

import java.util.Locale;

import ru.hzerr.fx.engine.core.interfaces.localization.ILocalizationMetaData;
import ru.hzerr.fx.engine.core.interfaces.path.ILocation;

public abstract class BaseLocalizationMetaData implements ILocalizationMetaData {

    protected static final Locale LOCALE_RU = Locale.of("ru", "RU");

    private final Locale locale;
    private final ILocation location;
    private final ConfigSyntax syntax;

    protected BaseLocalizationMetaData(Locale locale, ILocation location) {
        this.locale = locale;
        this.location = location;
        this.syntax = null;
    }

    protected BaseLocalizationMetaData(Locale locale, ILocation location, ConfigSyntax syntax) {
        this.locale = locale;
        this.location = location;
        this.syntax = syntax;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public ILocation getILocation() {
        return location;
    }
    @Override
    public ConfigSyntax getSyntax() { return syntax; }
}
