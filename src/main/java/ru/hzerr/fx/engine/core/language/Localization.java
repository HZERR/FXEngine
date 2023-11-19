package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.Config;
import ru.hzerr.fx.engine.configuration.fs.typesafe.FormattedConfiguration;
import ru.hzerr.fx.engine.configuration.fs.typesafe.IFormattedConfiguration;

public class Localization implements ILocalization {

    private BaseLocalizationMetaData metaData;
    private IFormattedConfiguration configuration;

    protected Localization(BaseLocalizationMetaData metaData, Config configuration) {
        this.metaData = metaData;
        this.configuration = new FormattedConfiguration(configuration);
    }

    @Override
    public BaseLocalizationMetaData getMetaData() {
        return metaData;
    }

    public void setConfiguration(IFormattedConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public IFormattedConfiguration getConfiguration() {
        return configuration;
    }
}
