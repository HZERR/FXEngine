package ru.hzerr.fx.engine.core.language.localization;

import ru.hzerr.fx.engine.core.annotation.RegisteredPrototype;
import ru.hzerr.fx.engine.core.interfaces.localization.IEngineLoggingLocalization;
import ru.hzerr.fx.engine.core.interfaces.localization.ILocalizationMetaData;
import ru.hzerr.fx.engine.core.interfaces.path.ILocation;
import ru.hzerr.fx.engine.core.language.LocalizationLoader;

@RegisteredPrototype
public class EngineLoggingLocalizationLoader extends LocalizationLoader<IEngineLoggingLocalization> {

    private EngineLoggingLocalizationLoader(ILocalizationMetaData metaData, ILocation location) {
        super(metaData, location);
    }

    private EngineLoggingLocalizationLoader(ILocalizationMetaData metaData, String location) {
        super(metaData, location);
    }

    @Override
    public IEngineLoggingLocalization load() {
        return new EngineLoggingLocalization(metaData, loadConfig());
    }
}
