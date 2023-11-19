package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.path.ILocation;

import java.util.Locale;

public abstract class ApplicationLocalizationMetaData extends BaseLocalizationMetaData {

    protected ApplicationLocalizationMetaData(Locale locale, ILocation location) {
        super(locale, location);
    }

    protected ApplicationLocalizationMetaData(Locale locale, ILocation location, ConfigSyntax syntax) {
        super(locale, location, syntax);
    }
}