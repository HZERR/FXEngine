package ru.hzerr.fx.engine.configuration;

import org.jetbrains.annotations.NotNull;
import ru.hzerr.fx.engine.annotation.LanguagePackMetaData;

public interface IApplicationConfiguration {

    /**
     * Данный метод должен возвращать квалификатор(значение) аннотации {@linkplain LanguagePackMetaData}, которая используется по-умолчанию!
     * @return id языка. А именно квалификатор(значение) аннотации {@linkplain LanguagePackMetaData}
     */
    @NotNull String getApplicationLanguageID();

}
