package ru.hzerr.fx.engine.core.context.registrar;

import ru.hzerr.fx.engine.core.ApplicationContextInitializationException;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.context.Ordered;
import ru.hzerr.fx.engine.core.context.initializer.IExtendedAnnotationConfigApplicationContextInitializer;

/**
 * This class is used to register all the initializers in a specific order
 *
 * @author HZERR
 * @version 1.0.8.1-Early
 * @since 2021-04-28
 */
@Ordered(4)
@SideOnly(Side.APPLICATION)
public abstract class ExtendedAnnotationConfigApplicationContextSequentialRegistrar implements IExtendedAnnotationConfigApplicationContextSequentialRegistrar {

    private IExtendedAnnotationConfigApplicationContext context;

    /**
     * This constructor initializes the context.
     *
     * @param context the application context
     */
    @Include
    public ExtendedAnnotationConfigApplicationContextSequentialRegistrar(IExtendedAnnotationConfigApplicationContext context) {
        this.context = context;
    }

    /**
     * This method is used to register an initializer.
     *
     * @param initializerClass the class of the initializer
     */
    @Override
    public final void register(Class<? extends IExtendedAnnotationConfigApplicationContextInitializer> initializerClass) {
        context.register(initializerClass);
        context.getBeanFactory().preInstantiateSingletons();
    }

    /**
     * This method is called to register all initializers
     */
    public abstract void onRegisterAll();

    /**
     * This method is used to initialize the application context.
     *
     * @throws ApplicationContextInitializationException if there is an error during initialization
     */
    @Override
    @SideOnly(Side.CORE)
    public final void onInitialize() throws ApplicationContextInitializationException {
        onRegisterAll();
    }
}
