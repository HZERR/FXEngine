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
    private Parent content;

    private ILogProvider engineLogProvider;

    public final void initialize() throws ResolveThemeException {
        onConnectDestroyEvent();
        FXEngine.getContext().getApplicationManager().register(id(), this);
        engineLogProvider.getLogger().debug("fxEngine.controller.initialize.controllerSuccessfullyRegistered", this.getClass().getSimpleName());
        FXEngine.getContext().getApplicationManager().setLanguage(FXEngine.getContext().getApplicationConfiguration().getLocale());
        onInit();
        FXEngine.getContext().getApplicationManager().applyTheme(this);
        engineLogProvider.getLogger().debug("fxEngine.controller.initialize.success", getClass().getSimpleName());
    }

    protected abstract void onInit();

    protected void onConnectDestroyEvent() {
        content.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                onDestroy();
            }
        });
    }

    protected void onDestroy() {
        FXEngine.getContext().getApplicationManager().unregister(id());
        engineLogProvider.getLogger().debug("fxEngine.controller.onDestroy", getClass().getSimpleName());
    }

    public final void applyTheme(ResolvedThemeLocation theme) {
        getContentAsParent().getStylesheets().clear();
        getContentAsParent().getStylesheets().add(0, theme.getStylesheet());
    }

    public abstract void onChangeLanguage(Localization localization);

    protected abstract String id();

    public FXEntity getMetaData() {
        return this.getClass().getAnnotation(FXEntity.class);
    }

    public <T extends Parent> T getContent(Class<T> cls) {
        return (T) content;
    }

    public Parent getContentAsParent() {
        return content;
    }

    void setEngineLogProvider(ILogProvider engineLogProvider) {
        this.engineLogProvider = engineLogProvider;
    }
}
