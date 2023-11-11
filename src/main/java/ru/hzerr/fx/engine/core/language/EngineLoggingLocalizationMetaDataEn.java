package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.core.path.typesafe.ConfigResourceLocation;

import java.util.Locale;

@Registered
public class EngineLoggingLocalizationMetaDataEn extends EngineLoggingLocalizationMetaData {
    public EngineLoggingLocalizationMetaDataEn() {
        super(Locale.ENGLISH, new ConfigResourceLocation("engine/language/en", "internationalization.json"), ConfigSyntax.JSON);
    }
}
