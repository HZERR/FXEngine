package ru.hzerr.fx.core.application.language.logging;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.language.LoggingLocalizationMetaData;
import ru.hzerr.fx.engine.core.path.typesafe.ConfigResourceLocation;

@Registered
public class LoggingLocalizationMetaDataRu extends LoggingLocalizationMetaData {
    protected LoggingLocalizationMetaDataRu() {
        super(LOCALE_RU, new ConfigResourceLocation("language/logging/ru-RU", "main.json"), ConfigSyntax.JSON);
    }
}
