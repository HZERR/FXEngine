package ru.hzerr.fx.engine.core;

public class IllegalClosedException extends RuntimeException {

    public IllegalClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}
