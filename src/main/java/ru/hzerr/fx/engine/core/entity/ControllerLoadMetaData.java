package ru.hzerr.fx.engine.core.entity;

public class ControllerLoadMetaData<C extends Controller> {

    private Class<C> controllerClass;

    private Class<?>[] parameterTypes;
    private Object[] arguments;

    private ControllerLoadMetaData(Class<C> controllerClass) {
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
    ControllerLoadMetaData<C> from(Class<C> controllerClass) {
        return new ControllerLoadMetaData<>(controllerClass);
    }
}
