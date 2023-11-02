package ru.hzerr.fx.engine.application.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.core.language.ApplicationLanguagePackMetaData;
import ru.hzerr.fx.engine.core.path.BaseLocation;

@Registered
public class LanguagePackMetaDataRu extends ApplicationLanguagePackMetaData {

    protected LanguagePackMetaDataRu() {
        super(LOCALE_RU, new BaseLocation("ru-RU"), ConfigSyntax.JSON);
    }
}
