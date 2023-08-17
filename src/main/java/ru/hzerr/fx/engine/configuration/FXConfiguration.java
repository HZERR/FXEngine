package ru.hzerr.fx.engine.configuration;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXConfiguration {

    public FXConfiguration() {
    }

    private Stage stage;

    private Scene scene;
    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    private ClassLoader resourceLoader = ClassLoader.getSystemClassLoader();

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
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
