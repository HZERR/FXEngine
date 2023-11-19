package ru.hzerr.fx.core.application.language.logging;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.language.LoggingLocalizationMetaData;
import ru.hzerr.fx.engine.core.path.BaseLocation;

import java.util.Locale;

@Registered
public class LoggingLocalizationMetaDataEn extends LoggingLocalizationMetaData {
    public LoggingLocalizationMetaDataEn() {
        super(Locale.ENGLISH, new BaseLocation("en-EN"), ConfigSyntax.JSON);
    }
}
