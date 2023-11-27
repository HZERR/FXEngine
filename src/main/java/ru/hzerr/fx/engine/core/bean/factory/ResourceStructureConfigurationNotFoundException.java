package ru.hzerr.fx.engine.core.bean.factory;

import org.springframework.beans.BeansException;

public class ResourceStructureConfigurationNotFoundException extends BeansException {
    public ResourceStructureConfigurationNotFoundException(String msg) {
        super(msg);
    }
}
