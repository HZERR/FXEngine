package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.fx.engine.annotation.FXEntity;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.language.ApplicationLanguagePackMetaData;
import ru.hzerr.fx.engine.core.language.BaseLanguagePackMetaData;
import ru.hzerr.fx.engine.core.language.LanguagePack;
import ru.hzerr.fx.engine.core.language.LanguagePackLoader;
import ru.hzerr.fx.engine.core.path.*;
import ru.hzerr.fx.engine.core.theme.Theme;

public abstract class Controller {

    public void initialize() {
        onInit();
        BaseLanguagePackMetaData currentLanguageMetaData = getApplicationLanguageMetaData();
        String currentLanguagePackageLocation = LocationTools.resolve(
                ResolvableLocation.of(
                    FXEngine.getContext().getStructureApplicationConfiguration().getApplicationInternationalizationPackage(),
                    NullSafeResolveLocationOptions.THROW_EXCEPTION
                ),
                ResolvableLocation.of(
                    currentLanguageMetaData.getILocation(),
                    NullSafeResolveLocationOptions.INSERT_EVERYWHERE
                ),
                SeparatorResolveLocationOptions.INSERT_END,
                Separator.SLASH_SEPARATOR
        );

        String currentLanguagePackLocation = LocationTools.resolve(
                ResolvableLocation.of(currentLanguagePackageLocation, NullSafeResolveLocationOptions.THROW_EXCEPTION),
                ResolvableLocation.of(getMetaData().internationalization(), NullSafeResolveLocationOptions.THROW_EXCEPTION),
                SeparatorResolveLocationOptions.DEFAULT,
                Separator.SLASH_SEPARATOR
        );

        LanguagePackLoader languagePackLoader = new LanguagePackLoader(currentLanguageMetaData, currentLanguagePackLocation);
        onChangeLanguage(languagePackLoader.load());
    }

    protected abstract void onInit();
    protected abstract void onDestroy();

    protected abstract void onChangeLanguage(LanguagePack languagePack);
    protected abstract void onChangeUI(Theme theme);

    private BaseLanguagePackMetaData getApplicationLanguageMetaData() {
        // todo change it
        return FXEngine.getContext().getBeansOfType(ApplicationLanguagePackMetaData.class).values().iterator().next();
//        String languageId = FXEngine.getContext().getApplicationConfiguration();
//        return FXEngine.getContext().getBeanByQualifier(languageId, BaseLanguagePackMetaData.class);
    }

    public FXEntity getMetaData() {
        return this.getClass().getAnnotation(FXEntity.class);
    }
}
