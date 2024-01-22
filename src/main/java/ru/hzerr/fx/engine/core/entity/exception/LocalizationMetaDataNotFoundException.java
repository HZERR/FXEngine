package ru.hzerr.fx.engine.core.entity.exception;

public class LocalizationMetaDataNotFoundException extends RuntimeException {

    public LocalizationMetaDataNotFoundException(String message) {
        super(message);
    }
    public LocalizationMetaDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
