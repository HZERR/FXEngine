package ru.hzerr.fx.engine.logging;

public class ConfigurableException extends Exception {

    public ConfigurableException(String message) {
        super(message);
    }

    public ConfigurableException(String message, Exception cause) {
        super(message, cause);
    }
}
