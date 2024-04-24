package ru.hzerr.fx.engine.core.entity.exception;

public class ProcessingConstructorException extends Exception {

    public ProcessingConstructorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessingConstructorException(Throwable cause) {
        super(cause);
    }
}
