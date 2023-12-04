package ru.hzerr.fx.engine.core.path.resolver;

import org.springframework.context.annotation.Scope;
import ru.hzerr.fx.engine.configuration.application.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.language.BaseLocalizationMetaData;
import ru.hzerr.fx.engine.core.path.*;

@Registered
@Scope("prototype")
public class ControllerLocalizationResolver implements Resolver {

    private BaseLocalizationMetaData metaData;
    private String internationalizationFile;
    private IResourceStructureConfiguration resourceStructureConfiguration;

    public ControllerLocalizationResolver(BaseLocalizationMetaData metaData, String internationalizationFile) {
        this.metaData = metaData;
        this.internationalizationFile = internationalizationFile;
    }

    @Override
    public String resolve() {
        String currentLanguagePackageLocation = LocationTools.resolve(
                ResolvableLocation.of(
                        resourceStructureConfiguration.getApplicationInternationalizationPackage(),
                        NullSafeResolveLocationOptions.THROW_EXCEPTION
                ),
                ResolvableLocation.of(
                        metaData.getILocation(),
                        NullSafeResolveLocationOptions.REMOVE_EVERYWHERE
                ),
                SeparatorResolveLocationOptions.REMOVE_EVERYWHERE,
                Separator.SLASH_SEPARATOR
        );

        return LocationTools.resolve(
                ResolvableLocation.of(currentLanguagePackageLocation, NullSafeResolveLocationOptions.THROW_EXCEPTION),
                ResolvableLocation.of(internationalizationFile, NullSafeResolveLocationOptions.THROW_EXCEPTION),
                SeparatorResolveLocationOptions.DEFAULT,
                Separator.SLASH_SEPARATOR
        );
    }

    @Include
    public void setResourceStructureConfiguration(IResourceStructureConfiguration resourceStructureConfiguration) {
        this.resourceStructureConfiguration = resourceStructureConfiguration;
    }
}
