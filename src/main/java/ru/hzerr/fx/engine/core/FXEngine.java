package ru.hzerr.fx.engine.core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.hzerr.fx.engine.configuration.application.IReadOnlyApplicationConfiguration;
import ru.hzerr.fx.engine.configuration.application.IReadOnlyClassLoaderProvider;
import ru.hzerr.fx.engine.configuration.application.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.configuration.application.IStructureConfiguration;
import ru.hzerr.fx.engine.configuration.logging.IReadOnlyLoggingConfiguration;
import ru.hzerr.fx.engine.core.context.AutomaticExtendedAnnotationConfigApplicationContextProvider;
import ru.hzerr.fx.engine.core.context.ExtendedAnnotationConfigApplicationContextProvider;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.theme.ThemeMetaData;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Необходимо заимплементить интерфейсы и пометить наследующие их классы аннотацией @Registered:<br>
 * 1) {@link IStructureConfiguration}<br>
 * 2) {@link IResourceStructureConfiguration}<br>
 * 3) {@link IReadOnlyApplicationConfiguration}<br>
 * 4) {@link IReadOnlyLoggingConfiguration}<br>
 * Опционально:<br>
 * 5) {@link IReadOnlyClassLoaderProvider}
 */
public abstract class FXEngine extends Application {

    protected static IExtendedAnnotationConfigApplicationContext context;
    private final AtomicBoolean closed = new AtomicBoolean(false);

    @Override
    public final void init() throws Exception {
        context = createApplicationContext();
        for (ThemeMetaData themeMetaData : context.getBeansOfType(ThemeMetaData.class).values()) {
            context.getFXEngineLogProvider().getLogger().info("fxEngine.init.registrationThemeInSystem", themeMetaData.getName());
            context.getApplicationManager().register(themeMetaData);
        }
        context.getFXEngineLogProvider().getLogger().info("fxEngine.init.loggerSuccessfullyConfigured");
        context.getFXEngineLogProvider().getLogger().info("fxEngine.init.selectedLoggingDirectory", getContext().getBean(IStructureConfiguration.class).getLogDirectory().getLocation());
        onInit();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                stop();
            } catch (Exception e) {
                throw new IllegalClosedException(context.isActive() ?
                        context.getEngineLocalizationProvider().getLocalization().getConfiguration().getString("fxEngine.init.closedException") :
                        "An error occurred stopping the application", e);
            }
        }));
        context.getFXEngineLogProvider().getLogger().info("fxEngine.init.engineSuccessfullyInitialized");
    }

    @Override
    public final void start(Stage stage) throws Exception {
        Scene scene = onStart(stage);
        stage.setScene(scene);
        context.setStage(stage);
        context.getStage().show();
        context.getFXEngineLogProvider().getLogger().info("fxEngine.start.engineSuccessfullyStarted");
    }

    @Override
    public final void stop() throws IOException {
        if (closed.compareAndSet(false, true)) {
            onClose();
            context.close();
        }
    }

    /**
     * Для реализации метода используйте вспомогательные методы и дергайте getContext():<br>
     * 1) {@linkplain #createAutomaticExtendedAnnotationConfigApplicationContextProvider(Class)}<br>
     * 2) {@linkplain #createExtendedAnnotationConfigApplicationContextProvider(String...)}<br>
     * @return настроенный контекст приложения
     */
    protected abstract IExtendedAnnotationConfigApplicationContext createApplicationContext();

    protected void onInit() throws Exception {
    }

    protected abstract Scene onStart(Stage stage) throws Exception;

    protected void onClose() throws IOException {
    }

    protected final IApplicationContextProvider<IExtendedAnnotationConfigApplicationContext> createExtendedAnnotationConfigApplicationContextProvider(String... basePackages) {
        return new ExtendedAnnotationConfigApplicationContextProvider(basePackages, true);
    }

    protected final IApplicationContextProvider<IExtendedAnnotationConfigApplicationContext> createAutomaticExtendedAnnotationConfigApplicationContextProvider(Class<? extends FXEngine> startupApplicationClass) {
        return new AutomaticExtendedAnnotationConfigApplicationContextProvider(startupApplicationClass);
    }

    public static IExtendedAnnotationConfigApplicationContext getContext() {
        return context;
    }

    public static <T extends IExtendedAnnotationConfigApplicationContext> T getContextAs(Class<T> contextClass) {
        return (T) context;
    }
}
