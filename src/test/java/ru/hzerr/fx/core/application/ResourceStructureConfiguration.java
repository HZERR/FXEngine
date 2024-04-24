package ru.hzerr.fx.core.application;

import org.jetbrains.annotations.Nullable;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.interfaces.engine.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.core.interfaces.path.ILocation;
import ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation;

/**
 * This class is used to configure the resource locations for the application.
 * It is registered as a Spring bean using the {@link Registered} annotation.
 */
@Registered
public class ResourceStructureConfiguration implements IResourceStructureConfiguration {

    /**
     * Returns the location of the FXML files.
     * @return the location of the FXML files
     */
    @Override
    public ILocation getFXMLPackage() {
        return RelativeDirectoryLocation.of("fxml");
//        return FXMLCombineRelativeDirectoryLocation.combine(RelativeDirectoryLocation.of("app1"), "fxml");
    }

    /**
     * Returns the location of the CSS files.
     * @return the location of the CSS files
     */
    @Override
    public ILocation getThemePackage() {
        return RelativeDirectoryLocation.of("theme");
//        return ThemeCombineRelativeDirectoryLocation.combine(RelativeDirectoryLocation.of("app1"), "theme");
    }

    /**
     * Returns the location of the internationalization files for the application.
     * @return the location of the internationalization files for the application, or null if no localization is required
     */
    @Nullable
    @Override
    public ILocation getApplicationInternationalizationPackage() {
        return RelativeDirectoryLocation.of("language");
    }

    /**
     * Returns the location of the internationalization files for the logging framework.
     * @return the location of the internationalization files for the logging framework, or null if no localization is required
     */
    @Nullable
    @Override
    public ILocation getApplicationLoggingInternationalizationPackage() {
        return null;
    }
}
