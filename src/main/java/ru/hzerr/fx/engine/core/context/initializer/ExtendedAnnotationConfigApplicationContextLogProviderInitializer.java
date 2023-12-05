package ru.hzerr.fx.engine.core.context.initializer;

import ru.hzerr.fx.engine.core.ApplicationContextInitializationException;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.context.InitializedFutureBean;
import ru.hzerr.fx.engine.core.context.Ordered;
import ru.hzerr.fx.engine.logging.ConfigurableException;
import ru.hzerr.fx.engine.logging.factory.FXApplicationLogProvider;
import ru.hzerr.fx.engine.logging.factory.FXEngineLogProvider;

import static ru.hzerr.fx.engine.core.context.ExtendedAnnotationConfigApplicationContext.*;

@Ordered(3)
@Registered
public class ExtendedAnnotationConfigApplicationContextLogProviderInitializer implements IExtendedAnnotationConfigApplicationContextInitializer, InitializedFutureBean {

    private IExtendedAnnotationConfigApplicationContext context;

    @Include
    public ExtendedAnnotationConfigApplicationContextLogProviderInitializer(IExtendedAnnotationConfigApplicationContext context) {
        this.context = context;
    }

    @Override
    public void onInitialize() throws ApplicationContextInitializationException {
        context.registerBean(APPLICATION_LOG_PROVIDER_BEAN_NAME, FXApplicationLogProvider.class);
        context.registerBean(ENGINE_LOG_PROVIDER_BEAN_NAME, FXEngineLogProvider.class);

        try {
            context.getFXEngineLogProvider().configure();
        } catch (ConfigurableException e) {
            throw new ApplicationContextInitializationException("Unable to create ApplicationContext. A logger configuration error has occurred", e);
        }
    }
}
