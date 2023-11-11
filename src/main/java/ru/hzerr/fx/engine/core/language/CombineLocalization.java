package ru.hzerr.fx.engine.core.language;

import ru.hzerr.collections.list.HList;
import ru.hzerr.fx.engine.configuration.typesafe.IFormattedConfiguration;

public class CombineLocalization implements ICombineLocalization {

    private HList<ILocalization> localizations;
    private IFormattedConfiguration configuration;

    public CombineLocalization(ILocalization... localizations) {
        this.localizations = HList.of(localizations);
        this.configuration = localizations[0].getConfiguration();

        for (int i = 1; i < localizations.length; i++) {
            this.configuration = configuration.withFallback(localizations[i].getConfiguration()).resolve();
        }
    }

    @Override
    public HList<ILocalization> getLocalizations() {
        return localizations;
    }

    @Override
    public IFormattedConfiguration getConfiguration() {
        return configuration;
    }
}
