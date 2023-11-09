package ru.hzerr.fx.engine.core.language;

import ru.hzerr.collections.list.HList;
import ru.hzerr.fx.engine.configuration.typesafe.IFormattedConfiguration;

public class MergedLanguagePack implements IMergedLanguagePack {

    private HList<ILanguagePack> languagePacks;
    private IFormattedConfiguration configuration;

    public MergedLanguagePack(LanguagePack... languagePacks) {
        this.languagePacks = HList.of(languagePacks);
        this.configuration = languagePacks[0].getConfiguration();

        for (int i = 1; i < languagePacks.length; i++) {
            this.configuration = (IFormattedConfiguration) configuration.withFallback(languagePacks[i].getConfiguration()).resolve();
        }
    }

    @Override
    public HList<ILanguagePack> getLanguagePacks() {
        return languagePacks;
    }

    @Override
    public IFormattedConfiguration getConfiguration() {
        return configuration;
    }
}
