package ru.hzerr.fx.engine.core.entity;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.FXController;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.annotation.metadata.EngineLogProvider;
import ru.hzerr.fx.engine.core.concurrent.ExtendedCompletableFuture;
import ru.hzerr.fx.engine.core.entity.exception.BeanControllerNotFoundException;
import ru.hzerr.fx.engine.core.entity.exception.FXMLNotFoundException;
import ru.hzerr.fx.engine.core.exception.FXMLLoadException;
import ru.hzerr.fx.engine.core.exception.LoadControllerException;
import ru.hzerr.fx.engine.core.interfaces.concurrent.IExtendedCompletionStage;
import ru.hzerr.fx.engine.core.interfaces.engine.IClassLoaderProvider;
import ru.hzerr.fx.engine.core.interfaces.engine.IEntityLoader;
import ru.hzerr.fx.engine.core.interfaces.engine.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.core.interfaces.entity.IController;
import ru.hzerr.fx.engine.core.interfaces.entity.IEntity;
import ru.hzerr.fx.engine.core.interfaces.entity.IPopupController;
import ru.hzerr.fx.engine.core.interfaces.entity.ISpringLoadMetaData;
import ru.hzerr.fx.engine.core.interfaces.logging.ILogProvider;
import ru.hzerr.fx.engine.core.path.resolver.EntityLocationResolver;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * Класс EntityLoader отвечает за загрузку сущностей в приложение.
 * Сущность представляет собой сочетание контроллера и представления узла.
 * Класс EntityLoader использует среду Spring для создания экземпляра контроллера, а затем использует класс JavaFX FXMLLoader для загрузки узла из файла FXML.
 *
 * Класс EntityLoader использует ExecutorService для асинхронного запуска задач, чтобы процесс загрузки не блокировал основной поток.
 */
@Registered
public class EntityLoader implements Closeable, IEntityLoader {

    private final ExecutorService service = Executors.newCachedThreadPool(
            new ThreadFactoryBuilder()
            .setDaemon(true)
            .setNameFormat("EntityLoader %d")
            .build()
    );

    private ILogProvider engineLogProvider;

    private IResourceStructureConfiguration resourceStructureConfiguration;

    private IClassLoaderProvider classLoaderProvider;

    public EntityLoader() {
        Runtime.getRuntime().addShutdownHook(Thread.ofVirtual().unstarted(service::shutdown));
    }

    /**
     * Класс контроллера, передаваемый сюда, обязательно должен иметь аннотации:<br>
     * 1) {@link Registered} или аналогичная<br>
     * 2) {@link FXController}<br>
     * 3) {@link FXEntity}<br>
     * @param loadData данные, для создания объекта контроллера с помощью Spring Application Context
     * @param parent представляющий из себя тип узла
     * @return загруженную сущность
     * @param <C> тип контроллера
     * @param <P> тип узла
     * @throws LoadControllerException в виде CompletionException в случае ошибки загрузки контроллера
     * @throws IOException в виде CompletionException в случае ошибки загрузки fxml
     * @see #load(ISpringLoadMetaData, Class)
     */
    @Override
    public <C extends IController, P extends Parent>
    IExtendedCompletionStage<IEntity<C, P>> loadAsync(ISpringLoadMetaData<C> loadData, Class<P> parent) {
        return ExtendedCompletableFuture.supplyAsync((Handler<IEntity<C, P>>) () -> load(loadData, parent), service);
    }

    /**
     * Класс контроллера, передаваемый сюда, обязательно должен иметь аннотации:<br>
     * 1) {@link Registered} или аналогичная<br>
     * 2) {@link FXController}<br>
     * 3) {@link FXEntity}<br>
     * @param loadData данные, для создания объекта контроллера с помощью Spring Application Context
     * @param parent представляющий из себя тип узла
     * @return загруженную сущность
     * @param <C> тип контроллера
     * @param <P> тип узла
     * @throws LoadControllerException в случае ошибки загрузки контроллера
     * @throws IOException в случае ошибки загрузки fxml
     * @see #load(ISpringLoadMetaData, Class)
     */
    @Override
    public <C extends IController, P extends Parent>
    IEntity<C, P> load(ISpringLoadMetaData<C> loadData, Class<P> parent) throws LoadControllerException, FXMLLoadException {
        return load(loadController(loadData), parent);
    }

    @Override
    public <C extends IPopupController, P extends Parent>
    IEntity<C, P> view(ISpringLoadMetaData<C> loadData, Class<P> parent) throws FXMLLoadException, LoadControllerException {
        IEntity<C, P> entity = load(loadController(loadData), parent);
        Platform.runLater(entity.getController()::view);
        return entity;
    }

    private <C extends IController, P extends Parent>
    IEntity<C, P> load(C controller, Class<P> parent) throws FXMLLoadException {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(controller);
        FXEntity controllerMetaData = controller.getClass().getAnnotation(FXEntity.class);
        String location = new EntityLocationResolver(resourceStructureConfiguration, controllerMetaData.fxml()).resolve();
        URL locationAsURL = classLoaderProvider.getApplicationResourceClassLoader().getResource(location);
        if (locationAsURL != null) {
            loader.setLocation(locationAsURL);
            P root;
            try {
                root = loader.load();
            } catch (IOException io) {
                throw new FXMLLoadException(io.getMessage(), io);
            }
            engineLogProvider.getLogger().info("fxEngine.entityLoader.loadEntity.entitySuccessfullyLoaded", controllerMetaData.fxml());
            return new Entity<>(controller, root);
        } else
            throw new FXMLNotFoundException("Unable to find fxml file at path '" + location + "'");
    }

    @NotNull
    private <C extends IController>
    C loadController(@NotNull ISpringLoadMetaData<C> loadData) throws BeanControllerNotFoundException {
        if (FXEngine.getContext().containsBean(loadData.getControllerClass())) {
            return FXEngine.getContext().getBean(loadData.getControllerClass(), loadData.getArguments());
        }

        throw new BeanControllerNotFoundException("Controller " + loadData.getControllerClass().getSimpleName() + " was not found");
    }

    @Override
    public void close() {
        service.shutdown();
    }

    @Include
    public void setEngineLogProvider(@EngineLogProvider ILogProvider engineLogProvider) {
        this.engineLogProvider = engineLogProvider;
    }

    @Include
    public void setClassLoaderProvider(IClassLoaderProvider classLoaderProvider) {
        this.classLoaderProvider = classLoaderProvider;
    }

    @Include
    public void setResourceStructureConfiguration(IResourceStructureConfiguration resourceStructureConfiguration) {
        this.resourceStructureConfiguration = resourceStructureConfiguration;
    }

    private interface Handler<T> extends Supplier<T> {

        @Override
        default T get() {
            try {
                return onReceive();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }

        T onReceive() throws Exception;
    }
}
