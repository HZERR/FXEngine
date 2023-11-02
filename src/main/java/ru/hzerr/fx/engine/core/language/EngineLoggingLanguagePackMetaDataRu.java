package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.path.typesafe.ConfigResourceLocation;

import java.util.Locale;

public class EngineLoggingLanguagePackMetaDataRu extends BaseLanguagePackMetaData {
    public EngineLoggingLanguagePackMetaDataRu() {
        super(new Locale("ru", "RU"), new ConfigResourceLocation("engine/language/ru", "internationalization.json"), ConfigSyntax.JSON);
    }
}
