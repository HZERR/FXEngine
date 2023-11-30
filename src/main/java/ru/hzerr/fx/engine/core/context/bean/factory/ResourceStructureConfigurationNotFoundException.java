package ru.hzerr.fx.engine.core.context.bean.factory;

import org.springframework.beans.BeansException;

public class ResourceStructureConfigurationNotFoundException extends BeansException {
    public ResourceStructureConfigurationNotFoundException(String msg) {
        super(msg);
    }
}
