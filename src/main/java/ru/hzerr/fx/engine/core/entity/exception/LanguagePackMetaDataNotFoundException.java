package ru.hzerr.fx.engine.core.entity.exception;

public class LanguagePackMetaDataNotFoundException extends RuntimeException {

    public LanguagePackMetaDataNotFoundException(String message) {
        super(message);
    }
    public LanguagePackMetaDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
