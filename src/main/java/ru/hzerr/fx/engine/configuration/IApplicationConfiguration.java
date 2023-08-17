package ru.hzerr.fx.engine.configuration;

import org.jetbrains.annotations.NotNull;

public interface IApplicationConfiguration {

    /**
     * Данный метод должен возвращать квалификатор(значение) аннотации {@linkplain ru.hzerr.fx.engine.annotation.LanguageMetaData}, которая используется по-умолчанию!
     * @return id языка. А именно квалификатор(значение) аннотации {@linkplain ru.hzerr.fx.engine.annotation.LanguageMetaData}
     */
    @NotNull String getLanguageID();

}
