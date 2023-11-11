package ru.hzerr.fx.engine.core.entity;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import ru.hzerr.fx.engine.annotation.FXEntity;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.language.Localization;
import ru.hzerr.fx.engine.core.theme.Theme;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

public abstract class Controller {

    @FXML
    protected Parent root;

    private ILogProvider logProvider = FXEngine.getContext().getFXEngineLogProvider();

    public void initialize() {
        onConnectDestroyEvent();
        onInit();
        FXEngine.getContext().getApplicationManager().register(id(), this);
        FXEngine.getContext().getApplicationManager().setLanguage(FXEngine.getContext().getApplicationConfiguration().getLocale());
        logProvider.getLogger().debug("fxEngine.controller.initialize", getClass().getSimpleName());
    }

    protected abstract void onInit();
    protected void onConnectDestroyEvent() {
        root.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                onDestroy();
            }
        });
    }
    protected void onDestroy() {
        FXEngine.getContext().getApplicationManager().unregister(id());
        logProvider.getLogger().debug("fxEngine.controller.onDestroy", getClass().getSimpleName());
    }

    protected abstract void onChangeLanguage(Localization languagePack);
    protected abstract void onChangeUI(Theme theme);
    protected abstract String id();

    public FXEntity getMetaData() {
        return this.getClass().getAnnotation(FXEntity.class);
    }

    public <T extends Parent> T getRoot(Class<T> cls) {
        return (T) root;
    }
}
