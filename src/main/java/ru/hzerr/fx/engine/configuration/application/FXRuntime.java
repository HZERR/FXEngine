package ru.hzerr.fx.engine.configuration.application;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXRuntime {

    public FXRuntime() {
    }

    private Stage stage;
    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    private ClassLoader resourceLoader = ClassLoader.getSystemClassLoader();

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return stage.getScene();
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassLoader getResourceLoader() {
        return resourceLoader;
    }

    public void setResourceLoader(ClassLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
