package ru.hzerr.fx.engine.core.context.bean.factory;

import org.springframework.beans.BeansException;

public class ReadOnlyApplicationConfigurationNotFoundException extends BeansException {
    public ReadOnlyApplicationConfigurationNotFoundException(String msg) {
        super(msg);
    }
}
