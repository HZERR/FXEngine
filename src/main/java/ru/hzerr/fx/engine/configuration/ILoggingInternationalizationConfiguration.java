package ru.hzerr.fx.engine.configuration;

import org.jetbrains.annotations.NotNull;
import ru.hzerr.fx.engine.core.language.LanguagePack;

import javax.annotation.Nullable;

public interface ILoggingInternationalizationConfiguration {

    boolean isInternationalizationEnabled();

    @NotNull LanguagePack getEngineLanguagePack();
    @Nullable LanguagePack getApplicationLanguagePack();
}
