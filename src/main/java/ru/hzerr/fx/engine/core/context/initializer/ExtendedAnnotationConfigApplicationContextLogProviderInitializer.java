package ru.hzerr.fx.engine.core.context.initializer;

import ru.hzerr.fx.engine.core.ApplicationContextInitializationException;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.context.Ordered;
import ru.hzerr.fx.engine.core.interfaces.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.interfaces.context.IExtendedAnnotationConfigApplicationContextInitializer;
import ru.hzerr.fx.engine.logging.provider.FXApplicationLogProvider;
import ru.hzerr.fx.engine.logging.provider.FXEngineLogProvider;

import java.lang.reflect.Method;

import static ru.hzerr.fx.engine.core.context.ExtendedAnnotationConfigApplicationContext.APPLICATION_LOG_PROVIDER_BEAN_NAME;
import static ru.hzerr.fx.engine.core.context.ExtendedAnnotationConfigApplicationContext.ENGINE_LOG_PROVIDER_BEAN_NAME;

@Ordered(3)
@Registered
public class ExtendedAnnotationConfigApplicationContextLogProviderInitializer implements IExtendedAnnotationConfigApplicationContextInitializer {

    private IExtendedAnnotationConfigApplicationContext context;

    @Include
    public ExtendedAnnotationConfigApplicationContextLogProviderInitializer(IExtendedAnnotationConfigApplicationContext context) {
        this.context = context;
    }

    @Override
    public void onInitialize() throws ApplicationContextInitializationException {
        context.registerBean(APPLICATION_LOG_PROVIDER_BEAN_NAME, FXApplicationLogProvider.class);
        context.registerBean(ENGINE_LOG_PROVIDER_BEAN_NAME, FXEngineLogProvider.class);

        context.getBean(APPLICATION_LOG_PROVIDER_BEAN_NAME);
        context.getBean(ENGINE_LOG_PROVIDER_BEAN_NAME);

        try {
            Method start = context.getFXEngineLogProvider().getClass().getDeclaredMethod("start");
            start.setAccessible(true);
            start.invoke(context.getFXEngineLogProvider());
            context.getFXEngineLogProvider().getLogger().info("fxEngine.init.loggerSuccessfullyConfigured");
        } catch (Exception e) {
            throw new ApplicationContextInitializationException("Unable to create ApplicationContext. A logger configuration error has occurred", e);
        }
    }
}
