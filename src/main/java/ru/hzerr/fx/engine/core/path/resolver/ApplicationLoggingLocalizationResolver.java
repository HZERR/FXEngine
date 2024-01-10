package ru.hzerr.fx.engine.core.path.resolver;

import ru.hzerr.fx.engine.configuration.application.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.configuration.logging.ILoggingConfiguration;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.as.ApplicationLoggingLocalizationMetaData;
import ru.hzerr.fx.engine.core.language.BaseLocalizationMetaData;
import ru.hzerr.fx.engine.core.path.*;

public class ApplicationLoggingLocalizationResolver implements Resolver {

    private ILoggingConfiguration applicationLoggingConfiguration;

    private IResourceStructureConfiguration resourceStructureConfiguration;

    private BaseLocalizationMetaData applicationLoggingLocalizationMetaData;

    @Override
    public String resolve() {
        String applicationLanguagePackageLocation = LocationTools.resolve(
                ResolvableLocation.of(
                        resourceStructureConfiguration.getApplicationLoggingInternationalizationPackage(),
                        NullSafeResolveLocationOptions.THROW_ILLEGAL_ARGUMENT_EXCEPTION
                ),
                ResolvableLocation.of(
                        applicationLoggingLocalizationMetaData.getILocation(),
                        NullSafeResolveLocationOptions.DEFAULT
                ),
                SeparatorResolveLocationOptions.DEFAULT,
                Separator.SLASH_SEPARATOR
        );

        return LocationTools.resolve(
                ResolvableLocation.of(
                        applicationLanguagePackageLocation,
                        NullSafeResolveLocationOptions.THROW_ILLEGAL_ARGUMENT_EXCEPTION
                ),
                ResolvableLocation.of(
                        applicationLoggingConfiguration.getApplicationLoggingLanguageFileName(),
                        NullSafeResolveLocationOptions.THROW_ILLEGAL_ARGUMENT_EXCEPTION
                ),
                SeparatorResolveLocationOptions.INSERT_START,
                Separator.SLASH_SEPARATOR
        );
    }

    @ApplicationLoggingLocalizationMetaData
    public void setApplicationLoggingLocalizationMetaData(BaseLocalizationMetaData applicationLoggingLocalizationMetaData) {
        this.applicationLoggingLocalizationMetaData = applicationLoggingLocalizationMetaData;
    }

    @Include
    public void setResourceStructureConfiguration(IResourceStructureConfiguration resourceStructureConfiguration) {
        this.resourceStructureConfiguration = resourceStructureConfiguration;
    }

    @Include
    public void setApplicationLoggingConfiguration(ILoggingConfiguration applicationLoggingConfiguration) {
        this.applicationLoggingConfiguration = applicationLoggingConfiguration;
    }
}
