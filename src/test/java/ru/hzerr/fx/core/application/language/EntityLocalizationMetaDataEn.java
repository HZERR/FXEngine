package ru.hzerr.fx.core.application.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.language.EntityLocalizationMetaData;
import ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation;

import java.util.Locale;

@Registered
public class EntityLocalizationMetaDataEn extends EntityLocalizationMetaData {

    protected EntityLocalizationMetaDataEn() {
        super(Locale.ENGLISH, RelativeDirectoryLocation.of("en-EN"), ConfigSyntax.JSON);
    }
}
