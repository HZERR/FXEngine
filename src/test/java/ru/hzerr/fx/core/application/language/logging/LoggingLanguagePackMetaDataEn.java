package ru.hzerr.fx.core.application.language.logging;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.core.language.LoggingLanguagePackMetaData;
import ru.hzerr.fx.engine.core.path.BaseLocation;

import java.util.Locale;

@Registered
public class LoggingLanguagePackMetaDataEn extends LoggingLanguagePackMetaData {
    public LoggingLanguagePackMetaDataEn() {
        super(Locale.ENGLISH, new BaseLocation("en-EN"), ConfigSyntax.JSON);
    }
}
