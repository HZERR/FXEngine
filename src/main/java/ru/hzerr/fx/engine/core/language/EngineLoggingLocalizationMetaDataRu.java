package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation;
import ru.hzerr.fx.engine.core.path.impl.EngineLoggingInternationalizationCombineRelativeDirectoryLocation;
import ru.hzerr.fx.engine.core.path.impl.EngineLoggingInternationalizationFileClassPathLocation;

@Registered
public class EngineLoggingLocalizationMetaDataRu extends EngineLoggingLocalizationMetaData {
    public EngineLoggingLocalizationMetaDataRu() {
        super(LOCALE_RU, EngineLoggingInternationalizationFileClassPathLocation.of(
                EngineLoggingInternationalizationCombineRelativeDirectoryLocation
                        .combine(RelativeDirectoryLocation.of("engine"), "language")
                        .combine("ru"), "internationalization.json"
        ), ConfigSyntax.JSON);
    }
}
