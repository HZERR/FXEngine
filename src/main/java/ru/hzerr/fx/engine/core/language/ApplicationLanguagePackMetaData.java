package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.path.ILocation;

import java.util.Locale;

public abstract class ApplicationLanguagePackMetaData extends BaseLanguagePackMetaData {

    protected ApplicationLanguagePackMetaData(Locale locale, ILocation location) {
        super(locale, location);
    }

    protected ApplicationLanguagePackMetaData(Locale locale, ILocation location, ConfigSyntax syntax) {
        super(locale, location, syntax);
    }
}
