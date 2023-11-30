package ru.hzerr.fx.engine.core.context.bean.factory;

import org.springframework.beans.BeansException;

public class ReadOnlyApplicationConfigurationWrongException extends BeansException {
    public ReadOnlyApplicationConfigurationWrongException(String msg) {
        super(msg);
    }
}
