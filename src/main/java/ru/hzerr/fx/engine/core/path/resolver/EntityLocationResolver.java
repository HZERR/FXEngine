package ru.hzerr.fx.engine.core.path.resolver;

import org.apache.commons.lang3.StringUtils;
import ru.hzerr.fx.engine.core.interfaces.engine.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.core.path.*;

public class EntityLocationResolver implements Resolver {

    private final IResourceStructureConfiguration resourceStructureConfiguration;

    private final String relativePath;

    public EntityLocationResolver(IResourceStructureConfiguration resourceStructureConfiguration, String relativePath) {
        this.resourceStructureConfiguration = resourceStructureConfiguration;
        this.relativePath = relativePath;
    }

    @Override
    public String resolve() {
        if (resourceStructureConfiguration.getFXMLPackage() == null) {
            return StringUtils.removeStart(relativePath, "/");
        }

        return LocationTools.resolve(
                ResolvableLocation.of(resourceStructureConfiguration.getFXMLPackage(), NullSafeResolveLocationOptions.THROW_ILLEGAL_ARGUMENT_EXCEPTION),
                ResolvableLocation.of(relativePath, NullSafeResolveLocationOptions.THROW_ILLEGAL_ARGUMENT_EXCEPTION),
                SeparatorResolveLocationOptions.START_REMOVE,
                Separator.SLASH_SEPARATOR
        );
    }
}
