package ru.hzerr.fx.engine.core.entity.exception;

import ru.hzerr.fx.engine.core.exception.LoadControllerException;

public class BeanControllerNotFoundException extends LoadControllerException {
    public BeanControllerNotFoundException(String message) {
        super(message);
    }
}
