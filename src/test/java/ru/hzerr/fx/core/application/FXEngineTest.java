package ru.hzerr.fx.core.application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.hzerr.fx.engine.core.entity.EntityLoader;
import ru.hzerr.fx.engine.core.entity.EntityLoader.ControllerLoadData;
import ru.hzerr.fx.engine.core.ExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.entity.Entity;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class FXEngineTest extends FXEngine {

    @Override
    protected ExtendedAnnotationConfigApplicationContext createApplicationContext() {
        return applicationContextProvider(new String[] {"ru.hzerr.fx.core.application"}).getApplicationContext();
    }

    @Override
    protected void onInit() throws Exception {
        EntityLoader.setClassLoader(ClassLoader.getSystemClassLoader());
//        context.registerBean("example1", Example.class);
//        context.getBean("example1", Example.class).setName("example1");
//        context.registerBean("example2", Example.class);
//        context.getBean("example2", Example.class).setName("example2");
//        context.registerBean(Example.class);
//        context.registerBean(Example.class);
//        System.out.println(context.getBeansOfType(Example.class).size());
//        System.out.println(context.getBean(Example.class));
    }

    @Override
    protected Scene onStart(Stage stage) {
        CompletableFuture<Entity<FXController, Parent>> mainFuture = EntityLoader.load("main", ControllerLoadData.from(FXController.class), Parent.class);

        Entity<FXController, Parent> mainEntity = mainFuture.join();
        context.getApplicationLogProvider().getLogger().info("Entity '{}' has been successfully loaded", mainEntity.getController().getMetaData().fxml());
        Scene scene = new Scene(mainEntity.getNode());
        return scene;
    }


    @Override
    protected void onClose() throws IOException {

    }
}
