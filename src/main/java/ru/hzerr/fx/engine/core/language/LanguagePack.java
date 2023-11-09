package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.Config;
import ru.hzerr.fx.engine.configuration.typesafe.FormattedConfiguration;
import ru.hzerr.fx.engine.configuration.typesafe.IFormattedConfiguration;

public class LanguagePack implements ILanguagePack {

    private BaseLanguagePackMetaData metaData;
    private IFormattedConfiguration configuration;

    protected LanguagePack(BaseLanguagePackMetaData metaData, Config configuration) {
        this.metaData = metaData;
        this.configuration = new FormattedConfiguration(configuration);
    }

    @Override
    public BaseLanguagePackMetaData getMetaData() {
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
