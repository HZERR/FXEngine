package ru.hzerr.fx.engine.core.bean.factory;

import org.springframework.beans.BeansException;

public class StructureConfigurationWrongException extends BeansException {
    public StructureConfigurationWrongException(String msg) {
        super(msg);
    }
}
