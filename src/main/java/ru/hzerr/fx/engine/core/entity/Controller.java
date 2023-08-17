package ru.hzerr.fx.engine.core.entity;

import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import ru.hzerr.fx.engine.annotation.FXEntity;
import ru.hzerr.fx.engine.configuration.IApplicationConfiguration;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.language.BaseLanguageMetaData;
import ru.hzerr.fx.engine.core.language.LanguagePack;
import ru.hzerr.fx.engine.core.language.LanguagePackLoader;
import ru.hzerr.fx.engine.core.theme.Theme;

public abstract class Controller {

    public void initialize() {
        onInit();
        BaseLanguageMetaData currentLanguageMetaData = getCurrentLanguageMetaData();
        onChangeLanguage(new LanguagePackLoader(currentLanguageMetaData, getControllerMetaData().internationalization()).load());
    }

    protected abstract void onInit();
    protected abstract void onDestroy();

    protected abstract void onChangeLanguage(LanguagePack languagePack);
    protected abstract void onChangeUI(Theme theme);

    private BaseLanguageMetaData getCurrentLanguageMetaData() {
        String languageId = FXEngine.getContext().getApplicationConfiguration().getLanguageID();
        return FXEngine.getContext().getBeanByQualifier(languageId, BaseLanguageMetaData.class);
    }

    private FXEntity getControllerMetaData() {
        return this.getClass().getAnnotation(FXEntity.class);
    }
}
