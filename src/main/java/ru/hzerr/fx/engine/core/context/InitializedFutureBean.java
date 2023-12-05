package ru.hzerr.fx.engine.core.context;

import org.springframework.beans.factory.InitializingBean;

public interface InitializedFutureBean extends InitializingBean {

    @Override
    default void afterPropertiesSet() throws Exception {
        onInitialize();
    }

    void onInitialize() throws Exception;
}
