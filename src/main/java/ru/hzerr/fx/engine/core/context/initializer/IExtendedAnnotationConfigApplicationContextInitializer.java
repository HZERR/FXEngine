package ru.hzerr.fx.engine.core.context.initializer;

import ru.hzerr.fx.engine.core.ApplicationContextInitializationException;

public interface IExtendedAnnotationConfigApplicationContextInitializer {

    void onInitialize() throws ApplicationContextInitializationException;
}
