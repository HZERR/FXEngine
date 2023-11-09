package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.core.path.typesafe.ConfigResourceLocation;

import java.util.Locale;

@Registered
public class EngineLoggingLanguagePackMetaDataRu extends EngineLoggingLanguagePackMetaData {
    public EngineLoggingLanguagePackMetaDataRu() {
        super(new Locale("ru", "RU"), new ConfigResourceLocation("engine/language/ru", "internationalization.json"), ConfigSyntax.JSON);
    }
}
