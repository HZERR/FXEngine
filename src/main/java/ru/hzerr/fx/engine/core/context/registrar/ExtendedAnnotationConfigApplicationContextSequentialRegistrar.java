package ru.hzerr.fx.engine.core.context.registrar;

import ru.hzerr.fx.engine.core.ApplicationContextInitializationException;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.context.Ordered;
import ru.hzerr.fx.engine.core.context.initializer.IExtendedAnnotationConfigApplicationContextInitializer;

@Ordered(4)
@SideOnly(Side.APPLICATION)
public abstract class ExtendedAnnotationConfigApplicationContextSequentialRegistrar implements IExtendedAnnotationConfigApplicationContextSequentialRegistrar {

    private IExtendedAnnotationConfigApplicationContext context;

    @Include
    public ExtendedAnnotationConfigApplicationContextSequentialRegistrar(IExtendedAnnotationConfigApplicationContext context) {
        this.context = context;
    }

    @Override
    public final void register(Class<? extends IExtendedAnnotationConfigApplicationContextInitializer> initializerClass) {
        context.register(initializerClass);
        context.getBeanFactory().preInstantiateSingletons();
    }

    public abstract void onRegisterAll();

    @Override
    @SideOnly(Side.CORE)
    public final void onInitialize() throws ApplicationContextInitializationException {
        onRegisterAll();
    }
}
