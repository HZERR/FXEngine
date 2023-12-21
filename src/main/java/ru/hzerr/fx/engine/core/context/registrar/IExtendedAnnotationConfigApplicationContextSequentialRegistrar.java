package ru.hzerr.fx.engine.core.context.registrar;

import ru.hzerr.fx.engine.core.context.initializer.IExtendedAnnotationConfigApplicationContextInitializer;

public interface IExtendedAnnotationConfigApplicationContextSequentialRegistrar extends IExtendedAnnotationConfigApplicationContextInitializer {

    void register(Class<? extends IExtendedAnnotationConfigApplicationContextInitializer> initializer);
}
