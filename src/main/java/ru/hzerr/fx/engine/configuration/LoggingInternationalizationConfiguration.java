package ru.hzerr.fx.engine.configuration;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.core.language.LanguagePack;

@Registered
public class LoggingInternationalizationConfiguration implements ILoggingInternationalizationConfiguration {

    private boolean internationalizationEnabled;
    private LanguagePack engineLanguagePack;
    private LanguagePack applicationLanguagePack;

    @Override
    public boolean isInternationalizationEnabled() {
        return internationalizationEnabled;
    }

    @NotNull
    @Override
    public LanguagePack getEngineLanguagePack() {
        return engineLanguagePack;
    }

    @Nullable
    @Override
    public LanguagePack getApplicationLanguagePack() {
        return applicationLanguagePack;
    }
}
