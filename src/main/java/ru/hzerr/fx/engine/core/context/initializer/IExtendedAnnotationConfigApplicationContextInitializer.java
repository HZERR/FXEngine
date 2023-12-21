package ru.hzerr.fx.engine.core.context.initializer;

import org.springframework.beans.factory.InitializingBean;
import ru.hzerr.fx.engine.core.ApplicationContextInitializationException;

public interface IExtendedAnnotationConfigApplicationContextInitializer extends InitializingBean {

    @Override
    default void afterPropertiesSet() {
        onInitialize();
    }

    void onInitialize() throws ApplicationContextInitializationException;
}
