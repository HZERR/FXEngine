package ru.hzerr.fx.engine.core.language.localization;

import ru.hzerr.fx.engine.core.annotation.RegisteredPrototype;
import ru.hzerr.fx.engine.core.interfaces.localization.IApplicationLoggingLocalization;
import ru.hzerr.fx.engine.core.interfaces.localization.ILocalizationMetaData;
import ru.hzerr.fx.engine.core.interfaces.path.ILocation;
import ru.hzerr.fx.engine.core.language.LocalizationLoader;

@RegisteredPrototype
public class ApplicationLoggingLocalizationLoader extends LocalizationLoader<IApplicationLoggingLocalization> {
    private ApplicationLoggingLocalizationLoader(ILocalizationMetaData metaData, ILocation location) {
        super(metaData, location);
    }

    // Create a new instance only through the Spring context.
    private ApplicationLoggingLocalizationLoader(ILocalizationMetaData metaData, String location) {
        super(metaData, location);
    }

    @Override
    public IApplicationLoggingLocalization load() {
        return new ApplicationLoggingLocalization(metaData, loadConfig());
    }
}
