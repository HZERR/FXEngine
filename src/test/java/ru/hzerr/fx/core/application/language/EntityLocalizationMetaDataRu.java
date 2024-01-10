package ru.hzerr.fx.core.application.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.language.EntityLocalizationMetaData;
import ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation;

@Registered
public class EntityLocalizationMetaDataRu extends EntityLocalizationMetaData {
    protected EntityLocalizationMetaDataRu() {
        super(LOCALE_RU, RelativeDirectoryLocation.of("ru-RU"), ConfigSyntax.JSON);
    }
}
