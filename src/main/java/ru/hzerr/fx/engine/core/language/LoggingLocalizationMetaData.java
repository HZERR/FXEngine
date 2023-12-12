package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.path.ILocation;

import java.util.Locale;

public abstract class LoggingLocalizationMetaData extends BaseLocalizationMetaData {

    /**
     * @param locale для "ru" локали используйте {@link #LOCALE_RU}
     * @param location {@link ru.hzerr.fx.engine.core.path.BaseLocation}, например, new BaseLocation("ru-RU")
     */
    protected LoggingLocalizationMetaData(Locale locale, ILocation location) {
        super(locale, location);
    }

    /**
     * @param locale для "ru" локали используйте {@link #LOCALE_RU}
     * @param location {@link ru.hzerr.fx.engine.core.path.BaseLocation}, например, new BaseLocation("ru-RU")
     */
    protected LoggingLocalizationMetaData(Locale locale, ILocation location, ConfigSyntax syntax) {
        super(locale, location, syntax);
    }
}
