package ru.hzerr.fx.engine.application.language.logging;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.core.language.LoggingLanguagePackMetaData;
import ru.hzerr.fx.engine.core.path.typesafe.ConfigResourceLocation;

import java.util.Locale;

@Registered
public class LoggingLanguagePackMetaDataRu extends LoggingLanguagePackMetaData {
    protected LoggingLanguagePackMetaDataRu() {
        super(new Locale("ru", "RU"), new ConfigResourceLocation("language/logging/ru-RU", "main.json"), ConfigSyntax.JSON);
    }
}
