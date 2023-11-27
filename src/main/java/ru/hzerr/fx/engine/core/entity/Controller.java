package ru.hzerr.fx.engine.core.entity;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.language.Localization;
import ru.hzerr.fx.engine.core.theme.ResolveThemeException;
import ru.hzerr.fx.engine.core.theme.ResolvedThemeLocation;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

public abstract class Controller {

    @FXML
    private Parent root;

    private ILogProvider engineLogProvider = FXEngine.getContext().getFXEngineLogProvider();

    public final void initialize() throws ResolveThemeException {
        onConnectDestroyEvent();
        FXEngine.getContext().getApplicationManager().register(id(), this);
        engineLogProvider.getLogger().debug("fxEngine.controller.initialize.controllerSuccessfullyRegistered", this.getClass().getSimpleName());
        engineLogProvider.getLogger().debug("fxEngine.controller.initialize.success", getClass().getSimpleName());
        FXEngine.getContext().getApplicationManager().setLanguage(FXEngine.getContext().getApplicationConfiguration().getLocale());
        onInit();
        changeTheme(FXEngine.getContext().getApplicationManager().resolve(this));
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
        engineLogProvider.getLogger().debug("fxEngine.controller.onDestroy", getClass().getSimpleName());
    }

    public final void changeTheme(ResolvedThemeLocation theme) {
        getRootAsParent().getStylesheets().clear();
        getRootAsParent().getStylesheets().add(0, theme.getStylesheet());
    }

    public abstract void onChangeLanguage(Localization languagePack);

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
