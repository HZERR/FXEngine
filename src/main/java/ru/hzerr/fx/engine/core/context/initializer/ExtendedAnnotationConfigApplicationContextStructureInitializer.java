package ru.hzerr.fx.engine.core.context.initializer;

import ru.hzerr.fx.engine.configuration.application.StructureInitializer;
import ru.hzerr.fx.engine.core.ApplicationContextInitializationException;
import ru.hzerr.fx.engine.core.InitializationException;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.context.InitializedBean;

public class ExtendedAnnotationConfigApplicationContextStructureInitializer implements IExtendedAnnotationConfigApplicationContextInitializer, InitializedBean {

    private IExtendedAnnotationConfigApplicationContext context;

    @Include
    public ExtendedAnnotationConfigApplicationContextStructureInitializer(IExtendedAnnotationConfigApplicationContext context) {
        this.context = context;
    }

    @Override
    public void onInitialize() throws ApplicationContextInitializationException {
        try {
            context.getBean(StructureInitializer.class).initialize();
        } catch (InitializationException e) {
            throw new ApplicationContextInitializationException("Unable to create ApplicationContext. An error occurred while configuring the application structure", e);
        }
    }
}
