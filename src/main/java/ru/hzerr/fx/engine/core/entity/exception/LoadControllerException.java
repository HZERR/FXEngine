package ru.hzerr.fx.engine.core.entity.exception;

public class LoadControllerException extends Exception {

    public LoadControllerException(String message) { super(message);}
    public LoadControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadControllerException(Throwable cause) {
        super(cause);
    }
}
