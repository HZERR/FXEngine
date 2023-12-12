package ru.hzerr.fx.engine.core.entity;

public class SpringLoadMetaData<C extends Controller> {

    private Class<C> controllerClass;
    private Object[] arguments;

    private SpringLoadMetaData(Class<C> controllerClass) {
        this.controllerClass = controllerClass;
    }

    private SpringLoadMetaData(Class<C> controllerClass, Object... arguments) {
        this.controllerClass = controllerClass;
        this.arguments = arguments;
    }

    public Class<C> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<C> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public static <C extends Controller>
    SpringLoadMetaData<C> from(Class<C> controllerClass) {
        return new SpringLoadMetaData<>(controllerClass);
    }

    public static <C extends Controller>
    SpringLoadMetaData<C> from(Class<C> controllerClass, Object... args) {
        return new SpringLoadMetaData<>(controllerClass, args);
    }
}
