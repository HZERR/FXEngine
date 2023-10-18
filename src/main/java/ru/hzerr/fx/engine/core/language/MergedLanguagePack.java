package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.Config;
import ru.hzerr.collections.list.HList;

public class MergedLanguagePack implements IMergedLanguagePack {

    private HList<ILanguagePack> languagePacks;
    private Config configuration;

    public MergedLanguagePack(LanguagePack... languagePacks) {
        this.languagePacks = HList.of(languagePacks);
        this.configuration = languagePacks[0].getConfiguration();

        for (int i = 1; i < languagePacks.length; i++) {
            this.configuration = configuration.withFallback(languagePacks[i].getConfiguration()).resolve();
        }
    }

    @Override
    public HList<ILanguagePack> getLanguagePacks() {
        return languagePacks;
    }

    @Override
    public Config getConfiguration() {
        return configuration;
    }
}
