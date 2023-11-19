package ru.hzerr.fx.engine.core.path.resolver;

import ru.hzerr.fx.engine.configuration.application.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.configuration.logging.ILoggingConfiguration;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.IncludeAs;
import ru.hzerr.fx.engine.core.annotation.Redefinition;
import ru.hzerr.fx.engine.core.language.BaseLocalizationMetaData;
import ru.hzerr.fx.engine.core.path.*;

@Redefinition(isRedefined = true)
public class ApplicationLoggingLocalizationResolver implements Resolver {

    @Include
    private ILoggingConfiguration applicationLoggingConfiguration;

    @Include
    private IResourceStructureConfiguration resourceStructureConfiguration;

    @IncludeAs("applicationLoggingLocalizationMetaData")
    private BaseLocalizationMetaData applicationLoggingLocalizationMetaData;

    @Override
    public String resolve() {
        String applicationLanguagePackageLocation = LocationTools.resolve(
                ResolvableLocation.of(
                        resourceStructureConfiguration.getApplicationLoggingInternationalizationPackage(),
                        NullSafeResolveLocationOptions.THROW_EXCEPTION
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
                        NullSafeResolveLocationOptions.THROW_EXCEPTION
                ),
                ResolvableLocation.of(
                        applicationLoggingConfiguration.getApplicationLoggingLanguageFileName(),
                        NullSafeResolveLocationOptions.THROW_EXCEPTION
                ),
                SeparatorResolveLocationOptions.INSERT_START,
                Separator.SLASH_SEPARATOR
        );
    }
}
