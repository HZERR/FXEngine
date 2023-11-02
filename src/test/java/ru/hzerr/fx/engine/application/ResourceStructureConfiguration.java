package ru.hzerr.fx.engine.application;

import org.jetbrains.annotations.Nullable;
import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.configuration.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.core.path.BaseLocation;

@Registered
public class ResourceStructureConfiguration implements IResourceStructureConfiguration {

    @Override
    public BaseLocation getFXMLPackage() {
        return new BaseLocation("fxml");
    }

    @Override
    public BaseLocation getThemePackage() {
        return new BaseLocation("theme");
    }

    @Nullable
    @Override
    public BaseLocation getApplicationInternationalizationPackage() {
        return new BaseLocation("language");
    }

    @Nullable
    @Override
    public BaseLocation getApplicationLoggingInternationalizationPackage() {
        return new BaseLocation("language/logging");
    }
}
