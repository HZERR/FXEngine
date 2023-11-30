package ru.hzerr.fx.engine.core.path.resolver;

import org.apache.commons.lang3.StringUtils;
import ru.hzerr.fx.engine.configuration.application.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.core.path.*;

public class EntityLocationResolver implements Resolver {

    private IResourceStructureConfiguration resourceStructureConfiguration;

    private String relativePath;

    public EntityLocationResolver(IResourceStructureConfiguration resourceStructureConfiguration, String relativePath) {
        this.resourceStructureConfiguration = resourceStructureConfiguration;
        this.relativePath = relativePath;
    }

    @Override
    public String resolve() {
        if (resourceStructureConfiguration.getFXMLPackage() == null) {
            return StringUtils.removeStartIgnoreCase(relativePath, "/");
        }

        return LocationTools.resolve(
                ResolvableLocation.of(resourceStructureConfiguration.getFXMLPackage(), NullSafeResolveLocationOptions.THROW_EXCEPTION),
                ResolvableLocation.of(relativePath, NullSafeResolveLocationOptions.THROW_EXCEPTION),
                SeparatorResolveLocationOptions.START_REMOVE,
                Separator.SLASH_SEPARATOR
        );
    }
}
