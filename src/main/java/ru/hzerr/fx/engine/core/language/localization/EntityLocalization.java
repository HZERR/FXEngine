package ru.hzerr.fx.engine.core.language.localization;

import com.typesafe.config.Config;
import ru.hzerr.fx.engine.core.language.BaseLocalizationMetaData;

public class EntityLocalization extends Localization {
    /**
     * Constructs a new instance of the Localization class.
     *
     * @param metaData      the metadata for the localization
     * @param configuration the configuration containing the localization data
     */
    public EntityLocalization(BaseLocalizationMetaData metaData, Config configuration) {
        super(metaData, configuration);
    }
}
