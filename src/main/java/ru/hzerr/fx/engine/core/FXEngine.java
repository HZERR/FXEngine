package ru.hzerr.fx.engine.core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.hzerr.fx.engine.configuration.FXConfiguration;
import ru.hzerr.fx.engine.configuration.IStructureConfiguration;
import ru.hzerr.fx.engine.configuration.StructureInitializer;
import ru.hzerr.fx.engine.core.entity.EntityLoader;

import java.io.IOException;

// TODO 1) Додумать и дописать логику сохранения entity, чтобы потом можно было менять у всех сразу язык, тему и тп
// TODO 2) ОПРЕДЕЛИТЬ, КАКИМ ОБРАЗОМ БУДЕТ ЗАКРЫВАТЬСЯ КОНТРОЛЛЕР
// TODO 3) ВЫДЕЛИТЬ ПУТЬ ДО FXML В КОНТРОЛЛЕРЕ. FXMLLOADER-Е НУЖНО СКОМБИНИРОВАТЬ ПУТЬ С ПУТЕМ ApplicationStructureConfiguration
// TODO 4) Куда девать ClassLoader-ы, stage, scene, версионнирование, название приложения и тп
// TODO 5) ПРОТЕСТИРОВАТЬ. ДОБАВИТЬ ВСЕ ПРОВЕРКИ. В ТЕСТОВОМ ВАРИАНТЕ ТУПО ВРЕМЕННО УБИРАТЬ ФАЙЛЫ, ЗАПУСКАТЬ И ЧЕКАТЬ. УДАЛИТЬ ВСЮ СТРУКТУРУ(ФАЙЛОВУЮ ВНЕШНЮЮ) ЕСЛИ НЕОБХОДИМО
// TODO 6) CombineLanguagePack НУЖНО ПРИВЕСТИ ВСЕ ДАННЫЕ К ОДНОЙ МЕТАДАТЕ. ПРИНЦИП - ОДИН ЭКЗЕМПЛЯР МЕТАДАННЫХ - НЕСКОЛЬКО КОНФИГУРАЦИЙ(НЕСКОЛЬКО LANGUAGE PACKS). РАЗРЕШИТЬ ЭТУ ПРОБЛЕМУ ПРИ ЗАГРУЗКЕ FXApplicationLogProvider. ИЗМЕНЕНИЯ В USER META DATA НЕ ДОЛЖНЫ КАСАТЬСЯ ENGINE META DATA. ПРИ ЭТОМ ОНИ МОГУТ БЫТЬ РАВНЫ
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
        context.registerBean(FXConfiguration.class);
        context.getFXEngineLogProvider().getLogger().debug("Logger has been successfully configured");
        context.getFXEngineLogProvider().getLogger().debug("Selected logging directory: {}", getContext().getBean(IStructureConfiguration.class).getLoggingDirectory().getLocation());
        onInit();
        context.getFXEngineLogProvider().getLogger().info("FXEngine has been successfully initialized");
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = onStart(stage);
        stage.setScene(scene);
        context.getBean(FXConfiguration.class).setScene(scene);
        context.getBean(FXConfiguration.class).setStage(stage);
        context.getBean(FXConfiguration.class).getStage().show();
        context.getFXEngineLogProvider().getLogger().info("FXEngine has been successfully started");
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
