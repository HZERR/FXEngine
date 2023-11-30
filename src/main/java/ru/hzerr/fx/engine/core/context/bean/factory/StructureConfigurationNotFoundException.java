package ru.hzerr.fx.engine.core.context.bean.factory;

import org.springframework.beans.BeansException;

public class StructureConfigurationNotFoundException extends BeansException {
    public StructureConfigurationNotFoundException(String msg) {
        super(msg);
    }
}
