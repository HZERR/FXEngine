package ru.hzerr.fx.engine.application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.hzerr.fx.engine.core.entity.EntityLoader;
import ru.hzerr.fx.engine.core.entity.EntityLoader.ControllerLoadData;
import ru.hzerr.fx.engine.core.ExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.entity.Entity;
import ru.hzerr.fx.engine.core.language.BaseLanguageMetaData;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class FXEngineTest extends FXEngine {

    @Override
    protected ExtendedAnnotationConfigApplicationContext createApplicationContext() {
        return applicationContextProvider(new String[] {"ru.hzerr.fx.engine.application"}).getApplicationContext();
    }

    @Override
    protected void onInit() throws Exception {
        EntityLoader.setClassLoader(ClassLoader.getSystemClassLoader());
    }

    @Override
    protected Scene onStart(Stage stage) {
        CompletableFuture<Entity<FXController, Parent>> mainFuture = EntityLoader.load("main", ControllerLoadData.from(FXController.class), Parent.class);

        Entity<FXController, Parent> mainEntity = mainFuture.join();
        Scene scene = new Scene(mainEntity.getNode());
        return scene;
    }


    @Override
    protected void onClose() throws IOException {

    }
}
