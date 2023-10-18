package ru.hzerr.fx.engine.application.language.logging;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.annotation.LanguagePackMetaData;
import ru.hzerr.fx.engine.core.language.BaseLanguagePackMetaData;
import ru.hzerr.fx.engine.core.path.typesafe.ConfigResourceLocation;

import java.util.Locale;

@LanguagePackMetaData("application.logging.language.ru")
public class LoggingLanguagePackMetaDataRu extends BaseLanguagePackMetaData {
    protected LoggingLanguagePackMetaDataRu() {
        super(new Locale("ru", "RU"), new ConfigResourceLocation("language/logging/ru-RU", "main.json"), ConfigSyntax.JSON);
    }
}
