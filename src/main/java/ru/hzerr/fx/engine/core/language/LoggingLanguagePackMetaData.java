package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.path.ILocation;

import java.util.Locale;

public abstract class LoggingLanguagePackMetaData extends BaseLanguagePackMetaData {

    protected LoggingLanguagePackMetaData(Locale locale, ILocation location) {
        super(locale, location);
    }

    protected LoggingLanguagePackMetaData(Locale locale, ILocation location, ConfigSyntax syntax) {
        super(locale, location, syntax);
    }
}
