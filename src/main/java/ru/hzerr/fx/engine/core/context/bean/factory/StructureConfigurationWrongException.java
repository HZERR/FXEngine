package ru.hzerr.fx.engine.core.context.bean.factory;

import org.springframework.beans.BeansException;

public class StructureConfigurationWrongException extends BeansException {
    public StructureConfigurationWrongException(String msg) {
        super(msg);
    }
}
