package ru.hzerr.fx.engine.core.language.localization;

import ru.hzerr.fx.engine.core.annotation.RegisteredPrototype;
import ru.hzerr.fx.engine.core.interfaces.localization.ILocalizationMetaData;
import ru.hzerr.fx.engine.core.interfaces.path.ILocation;
import ru.hzerr.fx.engine.core.language.LocalizationLoader;

@RegisteredPrototype
public class EntityLocalizationLoader extends LocalizationLoader<EntityLocalization> {
    private EntityLocalizationLoader(ILocalizationMetaData metaData, ILocation location) {
        super(metaData, location);
    }

    private EntityLocalizationLoader(ILocalizationMetaData metaData, String location) {
        super(metaData, location);
    }

    @Override
    public EntityLocalization load() {
        return new EntityLocalization(metaData, loadConfig());
    }
}
