package ru.hzerr.fx.engine.core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.hzerr.fx.engine.configuration.FXConfiguration;
import ru.hzerr.fx.engine.configuration.IStructureConfiguration;
import ru.hzerr.fx.engine.core.entity.ApplicationManager;
import ru.hzerr.fx.engine.core.entity.EntityLoader;

import java.io.IOException;

public abstract class FXEngine extends Application {

    protected static ExtendedAnnotationConfigApplicationContext context;

    /**
     * <p><font color="green"> НА ДАННОМ ЭТАПЕ ДОСТУП РАЗРЕШЕН: </font></p>
     * * {@linkplain FXEngine#context}<br>
     * * {@linkplain FXConfiguration#setClassLoader(ClassLoader)}<br>
     * * {@linkplain FXConfiguration#setResourceLoader(ClassLoader)}
     * <p><font color="red"> НА ДАННОМ ЭТАПЕ ДОСТУП ЗАПРЕЩЕН: </font></p>
     * * {@linkplain FXConfiguration#getScene()}<br>
     * * {@linkplain FXConfiguration#getStage()}<br>
     */
    @Override
    public void init() throws Exception {
        context = createApplicationContext();
        context.registerBean(ApplicationManager.class);
        context.registerBean(FXConfiguration.class);
        context.getFXEngineLogProvider().getLogger().info("fxEngine.init.loggerSuccessfullyConfigured");
        context.getFXEngineLogProvider().getLogger().info("fxEngine.init.selectedLoggingDirectory", getContext().getBean(IStructureConfiguration.class).getLogDirectory().getLocation());
        onInit();
        context.getFXEngineLogProvider().getLogger().info("fxEngine.init.engineSuccessfullyInitialized");
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = onStart(stage);
        stage.setScene(scene);
        context.getBean(FXConfiguration.class).setScene(scene);
        context.getBean(FXConfiguration.class).setStage(stage);
        context.getBean(FXConfiguration.class).getStage().show();
        context.getFXEngineLogProvider().getLogger().info("fxEngine.start.engineSuccessfullyStarted");
    }

    @Override
    public void stop() throws IOException {
        onClose();
        EntityLoader.close();
    }

    protected abstract ExtendedAnnotationConfigApplicationContext createApplicationContext();
    protected abstract void onInit() throws Exception;
    protected abstract Scene onStart(Stage stage) throws Exception;
    protected abstract void onClose() throws IOException;

    protected final IApplicationContextProvider<ExtendedAnnotationConfigApplicationContext> applicationContextProvider(String[] basePackages) {
        return new AnnotationConfigApplicationContextProvider(basePackages, true);
    }

    public static ExtendedAnnotationConfigApplicationContext getContext() {
        return context;
    }
}
