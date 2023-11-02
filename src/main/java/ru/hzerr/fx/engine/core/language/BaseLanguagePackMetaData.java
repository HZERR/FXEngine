package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.path.ILocation;

import java.util.Locale;

public abstract class BaseLanguagePackMetaData {

    protected static final Locale LOCALE_RU = new Locale("ru", "RU");

    private final Locale locale;
    private final ILocation location;
    private final ConfigSyntax syntax;

    protected BaseLanguagePackMetaData(Locale locale, ILocation location) {
        this.locale = locale;
        this.location = location;
        this.syntax = null;
    }

    protected BaseLanguagePackMetaData(Locale locale, ILocation location, ConfigSyntax syntax) {
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
