package ru.hzerr.fx.engine.core.language.localization;

import com.typesafe.config.Config;
import ru.hzerr.fx.engine.core.interfaces.localization.IEngineLoggingLocalization;
import ru.hzerr.fx.engine.core.interfaces.localization.ILocalizationMetaData;

public class EngineLoggingLocalization extends Localization implements IEngineLoggingLocalization {
    /**
     * Constructs a new instance of the EngineLoggingLocalization class.
     *
     * @param metaData      the metadata for the localization
     * @param configuration the configuration containing the localization data
     */
    public EngineLoggingLocalization(ILocalizationMetaData metaData, Config configuration) {
        super(metaData, configuration);
    }
}
