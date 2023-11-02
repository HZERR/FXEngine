package ru.hzerr.fx.engine.configuration;

import ru.hzerr.fx.engine.core.path.ILocation;

import javax.annotation.Nullable;

public interface IResourceStructureConfiguration {

    @Nullable ILocation getFXMLPackage();
    @Nullable ILocation getThemePackage();
    @Nullable ILocation getApplicationInternationalizationPackage();
    @Nullable ILocation getApplicationLoggingInternationalizationPackage();
}
