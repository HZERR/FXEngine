package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.Config;

public class LanguagePack implements ILanguagePack {

    private BaseLanguagePackMetaData metaData;
    private Config configuration;

    protected LanguagePack(BaseLanguagePackMetaData metaData, Config configuration) {
        this.metaData = metaData;
        this.configuration = configuration;
    }

    @Override
    public BaseLanguagePackMetaData getMetaData() {
        return metaData;
    }

    public void setConfiguration(Config configuration) {
        this.configuration = configuration;
    }

    @Override
    public Config getConfiguration() {
        return configuration;
    }
}
