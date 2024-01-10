package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.path.ILocation;

import java.util.Locale;

public abstract class LoggingLocalizationMetaData extends BaseLocalizationMetaData {

    /**
     * Для разрешения папок используйте {@link ru.hzerr.fx.engine.core.path.impl.LoggingLocalizationCombineRelativeDirectoryLocation}
     * @param locale для "ru" локали используйте {@link #LOCALE_RU}
     * @param location {@link ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation}, например, RelativeDirectoryLocation.of("ru-RU")
     * @see ru.hzerr.fx.engine.core.path.impl.LoggingLocalizationCombineRelativeDirectoryLocation
     */
    protected LoggingLocalizationMetaData(Locale locale, ILocation location) {
        super(locale, location);
    }

    /**
     * Для разрешения папок используйте {@link ru.hzerr.fx.engine.core.path.impl.LoggingLocalizationCombineRelativeDirectoryLocation}
     * @param locale для "ru" локали используйте {@link #LOCALE_RU}
     * @param location {@link ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation}, например, RelativeDirectoryLocation.of("ru-RU")
     * @param syntax синтаксис, который используется в файлах локализации
     * @see ru.hzerr.fx.engine.core.path.impl.LoggingLocalizationCombineRelativeDirectoryLocation
     */
    protected LoggingLocalizationMetaData(Locale locale, ILocation location, ConfigSyntax syntax) {
        super(locale, location, syntax);
    }
}
