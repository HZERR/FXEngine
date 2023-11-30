package ru.hzerr.fx.engine.core.entity;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;
import ru.hzerr.fx.engine.configuration.application.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.IncludeAs;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.entity.exception.*;
import ru.hzerr.fx.engine.core.path.resolver.EntityLocationResolver;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Registered
public class EntityLoader implements Closeable {

    private static final ExecutorService service = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon(true).setNameFormat("EntityLoader %d").build());

    @IncludeAs("engineLogProvider")
    private ILogProvider engineLogProvider;

    @Include
    private IResourceStructureConfiguration resourceStructureConfiguration;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(service::shutdown));
    }

    private final AtomicReference<ClassLoader> classLoader = new AtomicReference<>(ClassLoader.getSystemClassLoader());

    public void setClassLoader(ClassLoader classLoader) {
         this.classLoader.set(classLoader);
    }

    public <C extends Controller, P extends Parent> CompletableFuture<Entity<C, P>>
    load(ControllerLoadData<C> loadData, Class<P> parent) {
        return CompletableFuture.supplyAsync((Handler<Entity<C, P>>) () -> load0(loadData, parent), service);
    }

    private <C extends Controller, P extends Parent> Entity<C, P>
    load0(ControllerLoadData<C> loadData, Class<P> parent) throws LoadControllerException, IOException {
        FXMLLoader loader = new FXMLLoader();
        C controller = loadController(loadData);
        loader.setController(controller);
        FXEntity controllerMetaData = loadData.getControllerClass().getAnnotation(FXEntity.class);
        String location = new EntityLocationResolver(resourceStructureConfiguration, controllerMetaData.fxml()).resolve();
        URL locationAsURL = classLoader.get().getResource(location);
        if (locationAsURL != null) {
            loader.setLocation(locationAsURL);
            P root = loader.load();
            engineLogProvider.getLogger().info("fxEngine.entityLoader.loadEntity.entitySuccessfullyLoaded", controllerMetaData.fxml());
            return new Entity<>(controller, root);
        } else
            throw new FXMLNotFoundException("Unable to find fxml file at path '" + location + "'");
    }

    @NotNull
    private <C extends Controller>
    C loadController(@NotNull ControllerLoadData<C> loadData) throws ProcessingConstructorException, ConstructorNotFoundException, LoadAbstractClassException {
        Constructor<C> constructor;
        boolean withArgs = loadData.getParameterTypes() != null;
        if (withArgs) {
            try {
                constructor = loadData.getControllerClass().getDeclaredConstructor(loadData.getParameterTypes());
            } catch (NoSuchMethodException e) {
                String params = Arrays.stream(loadData.getParameterTypes()).map(Class::getName).collect(Collectors.joining(", "));
                throw new ConstructorNotFoundException(String.format("Конструктор класса '%s' с необходимыми параметрами не найден. Переданные параметры конструктора: %s", loadData.getControllerClass().getName(), params), e);
            }
            constructor.setAccessible(true);
            try {
                return constructor.newInstance(loadData.getArguments());
            } catch (InvocationTargetException e) {
                String params = Arrays.stream(loadData.getParameterTypes()).map(Class::getName).collect(Collectors.joining(", "));
                throw new ProcessingConstructorException(String.format("Произошла ошибка внутри конструктора класса '%s'. Переданные параметры конструктора: %s", loadData.getControllerClass().getName(), params), e);
            } catch (InstantiationException e) {
                throw new LoadAbstractClassException(String.format("Невозможно загрузить абстрактный класс '%s'", loadData.getControllerClass().getName()), e);
            } catch (IllegalAccessException e) {
                String params = Arrays.stream(loadData.getParameterTypes()).map(Class::getName).collect(Collectors.joining(", "));
                throw new IllegalAccessControllerException(String.format("Конструктор класса '%s' по каким-то причинам недоступен. Переданные параметры конструктора: %s", loadData.getControllerClass().getName(), params), e);
            }
        } else {
            try {
                constructor = loadData.getControllerClass().getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                throw new ConstructorNotFoundException(String.format("Конструктор класса '%s' без параметров не найден", loadData.getControllerClass().getName()), e);
            }
            constructor.setAccessible(true);
            try {
                return constructor.newInstance();
            } catch (InvocationTargetException e) {
                throw new ProcessingConstructorException(String.format("Произошла ошибка внутри конструктора без параметров класса '%s'", loadData.getControllerClass().getName()), e);
            } catch (InstantiationException e) {
                throw new LoadAbstractClassException(String.format("Невозможно загрузить абстрактный класс '%s'", loadData.getControllerClass().getName()), e);
            } catch (IllegalAccessException e) {
                throw new IllegalAccessControllerException(String.format("Конструктор без параметров класса '%s' по каким-то причинам недоступен", loadData.getControllerClass().getName()), e);
            }
        }
    }

    @Override
    public void close() {
        service.shutdown();
    }

    public static class ControllerLoadData<C extends Controller> {

        private Class<C> controllerClass;

        private Class<?>[] parameterTypes;
        private Object[] arguments;

        private ControllerLoadData(Class<C> controllerClass) {
            this.controllerClass = controllerClass;
        }

        public Class<C> getControllerClass() {
            return controllerClass;
        }

        public void setControllerClass(Class<C> controllerClass) {
            this.controllerClass = controllerClass;
        }

        public Class<?>[] getParameterTypes() {
            return parameterTypes;
        }

        public void setParameterTypes(Class<?>... parameterTypes) {
            this.parameterTypes = parameterTypes;
        }

        public Object[] getArguments() {
            return arguments;
        }

        public void setArguments(Object... arguments) {
            this.arguments = arguments;
        }

        public static <C extends Controller>
        ControllerLoadData<C> from(Class<C> controllerClass) {
            return new ControllerLoadData<>(controllerClass);
        }
    }
}
