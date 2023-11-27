package ru.hzerr.fx.engine.core;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import ru.hzerr.fx.engine.configuration.application.IStructureConfiguration;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;

@Registered
public class ExtendedAnnotationConfigApplicationContextInitializationListener implements ApplicationListener<ContextStartedEvent> {

    @Include
    private IExtendedAnnotationConfigApplicationContext context;

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        if (context.noContainsBean(IStructureConfiguration.class)) {
            System.out.println("NO CONTAINS BEAN IStructureConfiguration");
        }
    }
}
