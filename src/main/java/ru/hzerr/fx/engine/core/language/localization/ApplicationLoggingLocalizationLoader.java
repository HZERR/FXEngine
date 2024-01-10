package ru.hzerr.fx.engine.core.language.localization;

import ru.hzerr.fx.engine.core.annotation.RegisteredPrototype;
import ru.hzerr.fx.engine.core.language.BaseLocalizationMetaData;
import ru.hzerr.fx.engine.core.language.LocalizationLoader;
import ru.hzerr.fx.engine.core.path.ILocation;

@RegisteredPrototype
public class ApplicationLoggingLocalizationLoader extends LocalizationLoader<ApplicationLoggingLocalization> {
    private ApplicationLoggingLocalizationLoader(BaseLocalizationMetaData metaData, ILocation location) {
        super(metaData, location);
    }

    // Create a new instance only through the Spring context.
    private ApplicationLoggingLocalizationLoader(BaseLocalizationMetaData metaData, String location) {
        super(metaData, location);
    }

    @Override
    public ApplicationLoggingLocalization load() {
        return new ApplicationLoggingLocalization(metaData, loadConfig());
    }
}
