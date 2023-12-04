package ru.hzerr.fx.core.application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.entity.ControllerLoadMetaData;
import ru.hzerr.fx.engine.core.entity.Entity;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class FXEngineTest extends FXEngine {

    @Override
    protected IExtendedAnnotationConfigApplicationContext createApplicationContext() {
        return createAutomaticExtendedAnnotationConfigApplicationContextProvider(FXEngineTest.class).getContext();
    }

    @Override
    protected void onInit() throws Exception {
        context.getEntityLoader().setClassLoader(ClassLoader.getSystemClassLoader());
    }

    @Override
    protected Scene onStart(Stage stage) {
        CompletableFuture<Entity<FXController, Parent>> mainFuture = context.getEntityLoader().loadAsync(ControllerLoadMetaData.from(FXController.class), Parent.class);
        return new Scene(mainFuture.join().getNode());
    }


    @Override
    protected void onClose() throws IOException {

    }
}
