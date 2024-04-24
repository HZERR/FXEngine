package ru.hzerr.fx.core.application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.entity.Entity;
import ru.hzerr.fx.engine.core.entity.SpringLoadMetaData;
import ru.hzerr.fx.engine.core.interfaces.concurrent.IExtendedCompletionStage;
import ru.hzerr.fx.engine.core.interfaces.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.interfaces.entity.IEntity;

import java.io.IOException;

public class FXEngineTest extends FXEngine {

    @Override
    @NotNull
    protected IExtendedAnnotationConfigApplicationContext createApplicationContext() {
        return createExtendedAnnotationConfigApplicationContextProvider("ru.hzerr").getContext();
    }

    @Override
    protected void onInit() throws Exception {
    }

    @Override
    protected Scene onStart(Stage stage) {
        stage.setTitle("FXEngine Test");

        IExtendedCompletionStage<IEntity<BaseController, Parent>> mainFuture = context.getEntityLoader().loadAsync(SpringLoadMetaData.from(BaseController.class), Parent.class);
        return new Scene(mainFuture.join().getNode());
    }


    @Override
    protected void onClose() throws IOException {

    }
}
