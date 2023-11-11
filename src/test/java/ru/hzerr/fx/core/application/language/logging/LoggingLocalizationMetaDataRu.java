package ru.hzerr.fx.core.application.language.logging;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.core.language.LoggingLocalizationMetaData;
import ru.hzerr.fx.engine.core.path.typesafe.ConfigResourceLocation;

import java.util.Locale;

@Registered
public class LoggingLocalizationMetaDataRu extends LoggingLocalizationMetaData {
    protected LoggingLocalizationMetaDataRu() {
        super(new Locale("ru", "RU"), new ConfigResourceLocation("language/logging/ru-RU", "main.json"), ConfigSyntax.JSON);
    }
}
