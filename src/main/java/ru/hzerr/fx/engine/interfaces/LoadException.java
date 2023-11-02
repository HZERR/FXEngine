package ru.hzerr.fx.engine.interfaces;

public class LoadException extends Exception {
    public LoadException(String message, Exception e) {
        super(message, e);
    }
}
