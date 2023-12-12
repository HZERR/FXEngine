package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.path.ILocation;

import java.util.Locale;

public abstract class BaseLocalizationMetaData {

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

    public Locale getLocale() {
        return locale;
    }

    public ILocation getILocation() {
        return location;
    }
    public ConfigSyntax getSyntax() { return syntax; }
}
