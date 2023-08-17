package ru.hzerr.fx.engine.core;

import org.springframework.context.ApplicationContext;

public interface IApplicationContextProvider<T extends ApplicationContext> {

    T getApplicationContext();
}
