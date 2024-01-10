package ru.hzerr.fx.engine.core.language.localization;

import ru.hzerr.fx.engine.core.annotation.RegisteredPrototype;
import ru.hzerr.fx.engine.core.language.BaseLocalizationMetaData;
import ru.hzerr.fx.engine.core.language.LocalizationLoader;
import ru.hzerr.fx.engine.core.path.ILocation;

@RegisteredPrototype
public class EntityLocalizationLoader extends LocalizationLoader<EntityLocalization> {
    private EntityLocalizationLoader(BaseLocalizationMetaData metaData, ILocation location) {
        super(metaData, location);
    }

    private EntityLocalizationLoader(BaseLocalizationMetaData metaData, String location) {
        super(metaData, location);
    }

    @Override
    public EntityLocalization load() {
        return new EntityLocalization(metaData, loadConfig());
    }
}
