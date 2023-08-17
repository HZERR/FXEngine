package ru.hzerr.fx.engine.application;

import org.jetbrains.annotations.NotNull;
import ru.hzerr.fx.engine.annotation.BaseApplicationConfiguration;
import ru.hzerr.fx.engine.configuration.IApplicationConfiguration;

@BaseApplicationConfiguration
public class BaseApplicationConfig implements IApplicationConfiguration {

    @NotNull
    @Override
    public String getLanguageID() {
        return "language.meta.data.ru";
    }
}
