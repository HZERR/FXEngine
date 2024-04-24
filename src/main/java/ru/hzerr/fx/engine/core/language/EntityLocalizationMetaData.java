package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.interfaces.path.ILocation;

import java.util.Locale;

public abstract class EntityLocalizationMetaData extends BaseLocalizationMetaData {

    /**
     * @param locale для "ru" локали используйте {@link #LOCALE_RU}
     * @param location {@link ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation}, например, RelativeDirectoryLocation.of("ru-RU")
     */
    protected EntityLocalizationMetaData(Locale locale, ILocation location) {
        super(locale, location);
    }
    /**
     * @param locale для "ru" локали используйте {@link #LOCALE_RU}
     * @param location {@link ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation}, например, RelativeDirectoryLocation.of("ru-RU")
     */
    protected EntityLocalizationMetaData(Locale locale, ILocation location, ConfigSyntax syntax) {
        super(locale, location, syntax);
    }
}