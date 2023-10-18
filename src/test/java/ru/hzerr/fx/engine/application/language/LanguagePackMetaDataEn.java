package ru.hzerr.fx.engine.application.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.annotation.LanguagePackMetaData;
import ru.hzerr.fx.engine.core.language.BaseLanguagePackMetaData;
import ru.hzerr.fx.engine.core.path.BaseLocation;

import java.util.Locale;

@LanguagePackMetaData("language.meta.data.en")
public class LanguagePackMetaDataEn extends BaseLanguagePackMetaData {

    protected LanguagePackMetaDataEn() {
        super(Locale.ENGLISH, new BaseLocation("en-EN"), ConfigSyntax.JSON);
    }
}
