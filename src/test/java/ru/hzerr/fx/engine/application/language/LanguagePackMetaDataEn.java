package ru.hzerr.fx.engine.application.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.core.language.ApplicationLanguagePackMetaData;
import ru.hzerr.fx.engine.core.path.BaseLocation;

import java.util.Locale;

@Registered
public class LanguagePackMetaDataEn extends ApplicationLanguagePackMetaData {

    protected LanguagePackMetaDataEn() {
        super(Locale.ENGLISH, new BaseLocation("en-EN"), ConfigSyntax.JSON);
    }
}
