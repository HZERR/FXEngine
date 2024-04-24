package ru.hzerr.fx.engine.core.language.localization;

import com.typesafe.config.Config;
import ru.hzerr.fx.engine.core.interfaces.localization.IApplicationLoggingLocalization;
import ru.hzerr.fx.engine.core.interfaces.localization.ILocalizationMetaData;

public class ApplicationLoggingLocalization extends Localization implements IApplicationLoggingLocalization {
    /**
     * Constructs a new instance of the Localization class.
     *
     * @param metaData      the metadata for the localization
     * @param configuration the configuration containing the localization data
     */
    public ApplicationLoggingLocalization(ILocalizationMetaData metaData, Config configuration) {
        super(metaData, configuration);
    }
}
