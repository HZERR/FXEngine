package ru.hzerr.fx.engine.core.context.initializer;

import ru.hzerr.fx.engine.configuration.logging.ILoggingConfiguration;
import ru.hzerr.fx.engine.core.ApplicationContextInitializationException;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.RegisteredAs;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.context.Ordered;
import ru.hzerr.fx.engine.core.language.*;
import ru.hzerr.fx.engine.core.language.localization.LocalizationProvider;
import ru.hzerr.fx.engine.core.path.resolver.ApplicationLoggingLocalizationResolver;
import ru.hzerr.fx.engine.core.path.resolver.Resolver;

import static ru.hzerr.fx.engine.core.context.ExtendedAnnotationConfigApplicationContext.*;

@Ordered(2)
@RegisteredAs("localizationApplicationContextInitializer")
public class ExtendedAnnotationConfigApplicationContextLoggingLocalizationInitializer implements IExtendedAnnotationConfigApplicationContextInitializer {

    private IExtendedAnnotationConfigApplicationContext context;

    @Include
    public ExtendedAnnotationConfigApplicationContextLoggingLocalizationInitializer(IExtendedAnnotationConfigApplicationContext context) {
        this.context = context;
    }

    @Override
    public void onInitialize() throws ApplicationContextInitializationException {
        try {
            // register engine logging localization meta data
            registerEngineLoggingLocalizationMetaData();
            // register engine logging localization
            Localization engineLoggingLocalization = context.getBean(LocalizationLoader.class,
                    getEngineLoggingLocalizationMetaData(),
                    getEngineLoggingLocalizationMetaData().getILocation().getLocation()
            ).load();
            context.registerBean(ENGINE_LOGGING_LOCALIZATION_PROVIDER_BEAN_NAME, LocalizationProvider.class, engineLoggingLocalization);

            ILoggingConfiguration loggingConfiguration = context.getBean(ILoggingConfiguration.class);
            if (loggingConfiguration.isInternationalizationEnabled()) {
                // register application logging localization meta data
                registerApplicationLoggingLocalizationMetaData();
                // register application logging localization
                context.register(ApplicationLoggingLocalizationResolver.class);
                Resolver applicationLocalizationResolver = context.getBean(ApplicationLoggingLocalizationResolver.class);
                Localization applicationLoggingLocalization = context.getBean(LocalizationLoader.class,
                        getApplicationLoggingLocalizationMetaData(),
                        applicationLocalizationResolver.resolve()
                ).load();

                context.registerBean(APPLICATION_LOGGING_LOCALIZATION_PROVIDER_BEAN_NAME, LocalizationProvider.class, applicationLoggingLocalization);
            } else
                context.registerBean(APPLICATION_LOGGING_LOCALIZATION_PROVIDER_BEAN_NAME, LocalizationProvider.class, ((Localization)null));
        } catch (ApplicationLoggingLanguageMetaDataNotFoundException |
                 EngineLoggingLanguageMetaDataNotFoundException e) {
            throw new ApplicationContextInitializationException("Unable to create ApplicationContext. An error occurred while configuring the internationalization of the application", e);
        }
    }

    private EngineLoggingLocalizationMetaData getEngineLoggingLocalizationMetaData() {
        return context.getBean(ENGINE_LOGGING_LOCALIZATION_META_DATA_BEAN_NAME, EngineLoggingLocalizationMetaData.class);
    }

    private LoggingLocalizationMetaData getApplicationLoggingLocalizationMetaData() {
        return context.getBean(APPLICATION_LOGGING_LOCALIZATION_META_DATA_BEAN_NAME, LoggingLocalizationMetaData.class);
    }

    private void registerEngineLoggingLocalizationMetaData() throws EngineLoggingLanguageMetaDataNotFoundException {
        ILoggingConfiguration configuration = context.getBean(ILoggingConfiguration.class);
        for (EngineLoggingLocalizationMetaData metaData : context.getBeansOfType(EngineLoggingLocalizationMetaData.class).values()) {
            if (metaData.getLocale().equals(configuration.getEngineLocale())) {
                context.registerBean(ENGINE_LOGGING_LOCALIZATION_META_DATA_BEAN_NAME, EngineLoggingLocalizationMetaData.class, () -> metaData);
                return;
            }
        }

        throw new EngineLoggingLanguageMetaDataNotFoundException("The metadata of the language pack of the engine with the locale '" + configuration.getEngineLocale().toString() + "' was not found");
    }

    private void registerApplicationLoggingLocalizationMetaData() throws ApplicationLoggingLanguageMetaDataNotFoundException {
        ILoggingConfiguration configuration = context.getBean(ILoggingConfiguration.class);
        for (LoggingLocalizationMetaData metaData : context.getBeansOfType(LoggingLocalizationMetaData.class).values()) {
            if (metaData.getLocale().equals(configuration.getEngineLocale())) {
                context.registerBean(APPLICATION_LOGGING_LOCALIZATION_META_DATA_BEAN_NAME, LoggingLocalizationMetaData.class, () -> metaData);
                return;
            }
        }

        throw new ApplicationLoggingLanguageMetaDataNotFoundException("The metadata of the language pack for debugging the application with the language locale \"" + configuration.getApplicationLocale().toString() + "\" was not found");
    }
}

