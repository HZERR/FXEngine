package ru.hzerr.fx.engine.logging;

public class FactoryCloseableException extends RuntimeException {

    public FactoryCloseableException(Exception cause) {
        super("Ошибка закрытия провайдера логгирования", cause);
    }
}
