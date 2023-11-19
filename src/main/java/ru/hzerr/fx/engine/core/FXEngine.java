package ru.hzerr.fx.engine.core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.hzerr.fx.engine.configuration.application.FXRuntime;
import ru.hzerr.fx.engine.configuration.application.IStructureConfiguration;
import ru.hzerr.fx.engine.core.context.ExtendedAnnotationConfigApplicationContextProvider;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.entity.EntityLoader;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class FXEngine extends Application {

    protected static IExtendedAnnotationConfigApplicationContext context;
    private final AtomicBoolean CLOSED = new AtomicBoolean(false);

    /**
     * <p><font color="green"> НА ДАННОМ ЭТАПЕ ДОСТУП РАЗРЕШЕН: </font></p>
     * * {@linkplain FXEngine#context}<br>
     * * {@linkplain FXRuntime#setClassLoader(ClassLoader)}<br>
     * * {@linkplain FXRuntime#setResourceLoader(ClassLoader)}
     * <p><font color="red"> НА ДАННОМ ЭТАПЕ ДОСТУП ЗАПРЕЩЕН: </font></p>
     * * {@linkplain FXRuntime#getScene()}<br>
     * * {@linkplain FXRuntime#getStage()}<br>
     */
    @Override
    public void init() throws Exception {
        context = createApplicationContext();
        context.registerBean(FXRuntime.class);
        context.getFXEngineLogProvider().getLogger().info("fxEngine.init.loggerSuccessfullyConfigured");
        context.getFXEngineLogProvider().getLogger().info("fxEngine.init.selectedLoggingDirectory", getContext().getBean(IStructureConfiguration.class).getLogDirectory().getLocation());
        onInit();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                stop();
            } catch (Exception e) {
                throw new IllegalClosedException(context.getEngineLocalizationProvider().getLocalization().getConfiguration().getString("fxEngine.init.closedException"), e);
            }
        }));
        context.getFXEngineLogProvider().getLogger().info("fxEngine.init.engineSuccessfullyInitialized");
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = onStart(stage);
        stage.setScene(scene);
        context.setStage(stage);
        context.getStage().show();
        context.getFXEngineLogProvider().getLogger().info("fxEngine.start.engineSuccessfullyStarted");
    }

    @Override
    public void stop() throws IOException {
        if (CLOSED.compareAndSet(false, true)) {
            EntityLoader.close();
            onClose();
        }
    }

    protected abstract IExtendedAnnotationConfigApplicationContext createApplicationContext();
    protected abstract void onInit() throws Exception;
    protected abstract Scene onStart(Stage stage) throws Exception;
    protected abstract void onClose() throws IOException;

    protected final IApplicationContextProvider<IExtendedAnnotationConfigApplicationContext> applicationContextProvider(String[] basePackages) {
        return new ExtendedAnnotationConfigApplicationContextProvider(basePackages, true);
    }

    public static IExtendedAnnotationConfigApplicationContext getContext() {
        return context;
    }
}
