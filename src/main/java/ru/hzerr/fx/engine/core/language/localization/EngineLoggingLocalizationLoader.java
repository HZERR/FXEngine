package ru.hzerr.fx.engine.core.language.localization;

import ru.hzerr.fx.engine.core.annotation.RegisteredPrototype;
import ru.hzerr.fx.engine.core.language.BaseLocalizationMetaData;
import ru.hzerr.fx.engine.core.language.LocalizationLoader;
import ru.hzerr.fx.engine.core.path.ILocation;

@RegisteredPrototype
public class EngineLoggingLocalizationLoader extends LocalizationLoader<EngineLoggingLocalization> {

    private EngineLoggingLocalizationLoader(BaseLocalizationMetaData metaData, ILocation location) {
        super(metaData, location);
    }

    private EngineLoggingLocalizationLoader(BaseLocalizationMetaData metaData, String location) {
        super(metaData, location);
    }

    @Override
    public EngineLoggingLocalization load() {
        return new EngineLoggingLocalization(metaData, loadConfig());
    }
}
