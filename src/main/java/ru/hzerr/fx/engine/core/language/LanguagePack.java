package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.Config;

public class LanguagePack {

    private BaseLanguageMetaData metaData;
    private Config configuration;

    protected LanguagePack(BaseLanguageMetaData metaData, Config configuration) {
        this.metaData = metaData;
        this.configuration = configuration;
    }

    public BaseLanguageMetaData getMetaData() {
        return metaData;
    }

    public void setConfiguration(Config configuration) {
        this.configuration = configuration;
    }

    public Config getConfiguration() {
        return configuration;
    }
}
