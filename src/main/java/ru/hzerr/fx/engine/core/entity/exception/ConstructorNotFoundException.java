package ru.hzerr.fx.engine.core.entity.exception;

public class ConstructorNotFoundException extends Exception {

    public ConstructorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConstructorNotFoundException(Throwable cause) {
        super(cause);
    }
}
