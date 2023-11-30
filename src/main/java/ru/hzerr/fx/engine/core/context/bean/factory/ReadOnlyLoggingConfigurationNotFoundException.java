package ru.hzerr.fx.engine.core.context.bean.factory;

import org.springframework.beans.BeansException;

public class ReadOnlyLoggingConfigurationNotFoundException extends BeansException {
    public ReadOnlyLoggingConfigurationNotFoundException(String msg) {
        super(msg);
    }
}
