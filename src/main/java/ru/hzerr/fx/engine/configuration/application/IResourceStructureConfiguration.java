package ru.hzerr.fx.engine.configuration.application;

import ru.hzerr.fx.engine.configuration.IReadOnlyConfiguration;
import ru.hzerr.fx.engine.core.path.ILocation;

import javax.annotation.Nullable;

public interface IResourceStructureConfiguration extends IReadOnlyConfiguration {

    @Nullable ILocation getFXMLPackage();
    @Nullable ILocation getThemePackage();

    @Nullable ILocation getApplicationInternationalizationPackage();
    @Nullable ILocation getApplicationLoggingInternationalizationPackage();
}
