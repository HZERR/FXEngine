package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.core.path.typesafe.ConfigResourceLocation;

import java.util.Locale;

@Registered
public class EngineLoggingLanguagePackMetaDataEn extends EngineLoggingLanguagePackMetaData {
    public EngineLoggingLanguagePackMetaDataEn() {
        super(Locale.ENGLISH, new ConfigResourceLocation("engine/language/en", "internationalization.json"), ConfigSyntax.JSON);
    }
}
