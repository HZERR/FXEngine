package ru.hzerr.fx.engine.application;

import org.jetbrains.annotations.Nullable;
import ru.hzerr.fx.engine.annotation.StructureApplicationConfiguration;
import ru.hzerr.fx.engine.configuration.IStructureApplicationConfiguration;
import ru.hzerr.fx.engine.core.path.BaseLocation;

@StructureApplicationConfiguration
public class StructureApplicationConfig implements IStructureApplicationConfiguration {

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
