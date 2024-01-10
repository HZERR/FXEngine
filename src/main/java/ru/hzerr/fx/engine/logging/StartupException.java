package ru.hzerr.fx.engine.logging;

public class StartupException extends Exception {

    public StartupException(String message) {
        super(message);
    }

    public StartupException(String message, Exception cause) {
        super(message, cause);
    }
}
