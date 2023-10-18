package ru.hzerr.fx.engine.application.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.annotation.LanguagePackMetaData;
import ru.hzerr.fx.engine.core.language.BaseLanguagePackMetaData;
import ru.hzerr.fx.engine.core.path.BaseLocation;

import java.util.Locale;

@LanguagePackMetaData("language.meta.data.ru")
public class LanguagePackMetaDataRu extends BaseLanguagePackMetaData {

    protected LanguagePackMetaDataRu() {
        super(new Locale("ru", "RU"), new BaseLocation("ru-RU"), ConfigSyntax.JSON);
    }
}
