package ru.hzerr.fx.engine.core.path.resolver;

import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.RegisteredPrototype;
import ru.hzerr.fx.engine.core.interfaces.engine.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.core.interfaces.localization.ILocalizationMetaData;
import ru.hzerr.fx.engine.core.path.*;

@RegisteredPrototype
public class ControllerLocalizationResolver implements Resolver {

    private String internationalizationFile;
    private ILocalizationMetaData metaData;
    private IResourceStructureConfiguration resourceStructureConfiguration;

    private ControllerLocalizationResolver(ILocalizationMetaData metaData, String internationalizationFile) {
        this.metaData = metaData;
        this.internationalizationFile = internationalizationFile;
    }

    @Override
    public String resolve() {
        String currentLanguagePackageLocation = LocationTools.resolve(
                ResolvableLocation.of(
                        resourceStructureConfiguration.getApplicationInternationalizationPackage(),
                        NullSafeResolveLocationOptions.THROW_ILLEGAL_ARGUMENT_EXCEPTION
                ),
                ResolvableLocation.of(
                        metaData.getILocation(),
                        NullSafeResolveLocationOptions.REMOVE_EVERYWHERE
                ),
                SeparatorResolveLocationOptions.REMOVE_EVERYWHERE,
                Separator.SLASH_SEPARATOR
        );

        return LocationTools.resolve(
                ResolvableLocation.of(currentLanguagePackageLocation, NullSafeResolveLocationOptions.THROW_ILLEGAL_ARGUMENT_EXCEPTION),
                ResolvableLocation.of(internationalizationFile, NullSafeResolveLocationOptions.THROW_ILLEGAL_ARGUMENT_EXCEPTION),
                SeparatorResolveLocationOptions.DEFAULT,
                Separator.SLASH_SEPARATOR
        );
    }

    @Include
    public void setResourceStructureConfiguration(IResourceStructureConfiguration resourceStructureConfiguration) {
        this.resourceStructureConfiguration = resourceStructureConfiguration;
    }
}
