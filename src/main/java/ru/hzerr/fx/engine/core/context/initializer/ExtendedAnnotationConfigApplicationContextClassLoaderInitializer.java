package ru.hzerr.fx.engine.core.context.initializer;

import ru.hzerr.fx.engine.configuration.application.IReadOnlyClassLoaderProvider;
import ru.hzerr.fx.engine.core.ApplicationContextInitializationException;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.RegisteredAs;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.context.Ordered;

@Ordered(1)
@SideOnly(Side.CORE)
@RegisteredAs("classLoaderApplicationContextInitializer")
public class ExtendedAnnotationConfigApplicationContextClassLoaderInitializer implements IExtendedAnnotationConfigApplicationContextInitializer {

    private IExtendedAnnotationConfigApplicationContext context;

    @Include
    public ExtendedAnnotationConfigApplicationContextClassLoaderInitializer(IExtendedAnnotationConfigApplicationContext context) {
        this.context = context;
    }

    @Override
    public void onInitialize() throws ApplicationContextInitializationException {
        context.fetchBean(IReadOnlyClassLoaderProvider.class).ifPresent(classLoaderProvider -> {
            context.getClassLoaderProvider().setApplicationClassLoader(classLoaderProvider.getApplicationClassLoader());
            context.getClassLoaderProvider().setSpringContextClassLoader(classLoaderProvider.getSpringContextClassLoader());
            context.getClassLoaderProvider().setApplicationResourceClassLoader(classLoaderProvider.getApplicationResourceClassLoader());
        });
    }
}
