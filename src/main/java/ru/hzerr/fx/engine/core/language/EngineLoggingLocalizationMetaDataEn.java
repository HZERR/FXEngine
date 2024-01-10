package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation;
import ru.hzerr.fx.engine.core.path.impl.EngineLoggingInternationalizationCombineRelativeDirectoryLocation;
import ru.hzerr.fx.engine.core.path.impl.EngineLoggingInternationalizationFileClassPathLocation;

import java.util.Locale;

@Registered
public class EngineLoggingLocalizationMetaDataEn extends EngineLoggingLocalizationMetaData {
    public EngineLoggingLocalizationMetaDataEn() {
        super(Locale.ENGLISH, EngineLoggingInternationalizationFileClassPathLocation.of(
                EngineLoggingInternationalizationCombineRelativeDirectoryLocation
                        .combine(RelativeDirectoryLocation.of("engine"), "language")
                        .combine("en"), "internationalization.json"
        ), ConfigSyntax.JSON);
    }
}
