package ru.hzerr.fx.engine.application.language.logging;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.annotation.LanguagePackMetaData;
import ru.hzerr.fx.engine.core.language.BaseLanguagePackMetaData;
import ru.hzerr.fx.engine.core.path.BaseLocation;

import java.util.Locale;

@LanguagePackMetaData("application.logging.language.en")
public class LoggingLanguagePackMetaDataEn extends BaseLanguagePackMetaData {
    public LoggingLanguagePackMetaDataEn() {
        super(Locale.ENGLISH, new BaseLocation("en-EN"), ConfigSyntax.JSON);
    }
}
