package ru.hzerr.fx.engine.core.entity;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.language.Localization;
import ru.hzerr.fx.engine.core.theme.Theme;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

public abstract class Controller {

    @FXML
    private Parent root;

    private ILogProvider logProvider = FXEngine.getContext().getFXEngineLogProvider();

    public final void initialize() {
        onConnectDestroyEvent();
        FXEngine.getContext().getApplicationManager().register(id(), this);
        logProvider.getLogger().debug("fxEngine.controller.initialize", getClass().getSimpleName());
        FXEngine.getContext().getApplicationManager().setLanguage(FXEngine.getContext().getApplicationConfiguration().getLocale());
        onInit();
        onChangeUI(FXEngine.getContext().getApplicationManager().getTheme());
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

    protected final void onChangeUI(Theme theme) {
        getRootAsParent().getStylesheets().clear();
        getRootAsParent().getStylesheets().add(0, theme.getStylesheet(getClass()));
    }

    protected abstract void onChangeLanguage(Localization languagePack);

    protected abstract String id();

    public FXEntity getMetaData() {
        return this.getClass().getAnnotation(FXEntity.class);
    }

    public <T extends Parent> T getRoot(Class<T> cls) {
        return (T) root;
    }

    public Parent getRootAsParent() {
        return root;
    }
}
