package ru.hzerr.fx.engine.core.entity;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;
import ru.hzerr.fx.engine.core.async.Handler;
import ru.hzerr.fx.engine.annotation.FXEntity;
import ru.hzerr.fx.engine.core.entity.exception.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public final class EntityLoader {

    private static final ExecutorService service = Executors.newCachedThreadPool();

    private static final AtomicReference<ClassLoader> classLoader = new AtomicReference<>(ClassLoader.getSystemClassLoader());

    public static void setClassLoader(ClassLoader classLoader) {
         EntityLoader.classLoader.set(classLoader);
    }

    public static <C extends Controller, P extends Parent> CompletableFuture<Entity<C, P>>
    load(String id, ControllerLoadData<C> loadData, Class<P> parent) {
        return CompletableFuture.supplyAsync((Handler<Entity<C, P>>) () -> load0(id, loadData, parent), service);
    }

    private static <C extends Controller, P extends Parent> Entity<C, P>
    load0(String id, ControllerLoadData<C> loadData, Class<P> parent) throws LoadControllerException, IOException {
        FXMLLoader loader = new FXMLLoader();
        C controller = loadController(loadData);
        loader.setController(controller);
        FXEntity controllerMetaData = loadData.getControllerClass().getAnnotation(FXEntity.class);
        loader.setLocation(classLoader.get().getResource(controllerMetaData.fxml()));
        return new Entity<>(controller, loader.load());
    }

    @NotNull
    private static <C extends Controller>
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

    public static void close() {
        service.shutdown();
    }

    public static class ControllerLoadData<CONTROLLER extends Controller> {

        private Class<CONTROLLER> controllerClass;

        private Class<?>[] parameterTypes;
        private Object[] arguments;

        private ControllerLoadData(Class<CONTROLLER> controllerClass) {
            this.controllerClass = controllerClass;
        }

        public Class<CONTROLLER> getControllerClass() {
            return controllerClass;
        }

        public void setControllerClass(Class<CONTROLLER> controllerClass) {
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

        public static <CONTROLLER extends Controller>
        ControllerLoadData<CONTROLLER> from(Class<CONTROLLER> controllerClass) {
            return new ControllerLoadData<>(controllerClass);
        }
    }
}
