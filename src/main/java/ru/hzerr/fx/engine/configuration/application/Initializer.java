package ru.hzerr.fx.engine.configuration.application;

import ru.hzerr.fx.engine.core.InitializationException;

public interface Initializer {

    void initialize() throws InitializationException;
}
