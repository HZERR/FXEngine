package ru.hzerr.fx.engine.core.language.localization;

import com.typesafe.config.Config;
import ru.hzerr.fx.engine.configuration.fs.typesafe.FormattedConfiguration;
import ru.hzerr.fx.engine.core.interfaces.localization.IFormattedConfiguration;
import ru.hzerr.fx.engine.core.interfaces.localization.ILocalization;
import ru.hzerr.fx.engine.core.interfaces.localization.ILocalizationMetaData;

/**
 * This class provides localization support for the application. It loads the localization data from the configuration file and provides methods for retrieving localized strings.
 */
public class Localization implements ILocalization {

    private ILocalizationMetaData metaData;
    private IFormattedConfiguration configuration;

    /**
     * Constructs a new instance of the Localization class.
     *
     * @param metaData the metadata for the localization
     * @param configuration the configuration containing the localization data
     */
    protected Localization(ILocalizationMetaData metaData, Config configuration) {
        this.metaData = metaData;
        this.configuration = new FormattedConfiguration(configuration);
    }

    /**
     * Gets the metadata for the localization.
     *
     * @return the metadata for the localization
     */
    @Override
    public ILocalizationMetaData getMetaData() {
        return metaData;
    }

    /**
     * Sets the configuration.
     *
     * @param configuration the configuration
     */
    public void setConfiguration(IFormattedConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Gets the configuration.
     *
     * @return the configuration
     */
    @Override
    public IFormattedConfiguration getConfiguration() {
        return configuration;
    }
}
