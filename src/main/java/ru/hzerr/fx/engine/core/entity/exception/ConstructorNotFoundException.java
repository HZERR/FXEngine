package ru.hzerr.fx.engine.core.entity.exception;

public class ConstructorNotFoundException extends LoadControllerException {

    public ConstructorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConstructorNotFoundException(Throwable cause) {
        super(cause);
    }
}
