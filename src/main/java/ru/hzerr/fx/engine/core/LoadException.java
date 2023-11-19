package ru.hzerr.fx.engine.core;

public class LoadException extends Exception {
    public LoadException(String message, Exception e) {
        super(message, e);
    }

    public LoadException(String message) {
        super(message);
    }
}
