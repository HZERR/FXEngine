package ru.hzerr.fx.engine.core.entity.exception;

public class FXMLNotFoundException extends RuntimeException {

    public FXMLNotFoundException(String message) {
        super(message);
    }
    public FXMLNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
